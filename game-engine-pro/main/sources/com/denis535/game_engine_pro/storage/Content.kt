package com.denis535.game_engine_pro.storage

import cnames.structs.*
import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public class Content : AutoCloseable {
    public sealed class LoadAssetAsyncResult {
        public class Completed(public val Data: ByteArray) : LoadAssetAsyncResult()
        public class Faulted(public val Error: String) : LoadAssetAsyncResult()
        public object Canceled : LoadAssetAsyncResult()
    }

    @OptIn(ExperimentalForeignApi::class)
    private val NativeStorage: CPointer<SDL_Storage>

    @OptIn(ExperimentalForeignApi::class)
    private val NativeAsyncIOQueue: CPointer<SDL_AsyncIOQueue>

    @OptIn(ExperimentalForeignApi::class)
    public val IsReady: Boolean
        get() = SDL_StorageReady(this.NativeStorage).SDL_CheckError()

    @OptIn(ExperimentalForeignApi::class)
    public constructor(path: String?) {
        this.NativeStorage = run {
            val properties = SDL_CreateProperties().SDL_CheckError()
            val result = SDL_OpenTitleStorage(path, properties).SDL_CheckError()!!
            SDL_DestroyProperties(properties).SDL_CheckError()
            result
        }
        this.NativeAsyncIOQueue = SDL_CreateAsyncIOQueue().SDL_CheckError()!!
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        SDL_DestroyAsyncIOQueue(this.NativeAsyncIOQueue).SDL_CheckError()
        SDL_CloseStorage(this.NativeStorage).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    internal fun Process() {
        memScoped {
            val outcome = this.alloc<SDL_AsyncIOOutcome>()
            while (SDL_GetAsyncIOResult(this@Content.NativeAsyncIOQueue, outcome.ptr).SDL_CheckError()) {
                when (outcome.result) {
                    SDL_AsyncIOResult.SDL_ASYNCIO_COMPLETE -> {
                        val callbackStableRef = outcome.userdata!!.asStableRef<(LoadAssetAsyncResult) -> Unit>()
                        val data = outcome.buffer!!.readBytes(outcome.bytes_transferred.toInt())
                        SDL_free(outcome.buffer).SDL_CheckError()
                        callbackStableRef.get().invoke(LoadAssetAsyncResult.Completed(data))
                        callbackStableRef.dispose()
                    }
                    SDL_AsyncIOResult.SDL_ASYNCIO_FAILURE -> {
                        val callbackStableRef = outcome.userdata!!.asStableRef<(LoadAssetAsyncResult) -> Unit>()
                        val error = SDL.GetError() ?: "unknown error"
                        callbackStableRef.get().invoke(LoadAssetAsyncResult.Faulted(error))
                        callbackStableRef.dispose()
                    }
                    SDL_AsyncIOResult.SDL_ASYNCIO_CANCELED -> {
                        val callbackStableRef = outcome.userdata!!.asStableRef<(LoadAssetAsyncResult) -> Unit>()
                        callbackStableRef.get().invoke(LoadAssetAsyncResult.Canceled)
                        callbackStableRef.dispose()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsAsset(path: String): Boolean {
        memScoped {
            val info = this.alloc<SDL_PathInfo>()
            if (SDL_GetStoragePathInfo(this@Content.NativeStorage, path, info.ptr).SDL_CheckError()) {
                return info.type == SDL_PathType.SDL_PATHTYPE_FILE
            }
        }
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsDirectory(path: String): Boolean {
        memScoped {
            val info = this.alloc<SDL_PathInfo>()
            if (SDL_GetStoragePathInfo(this@Content.NativeStorage, path, info.ptr).SDL_CheckError()) {
                return info.type == SDL_PathType.SDL_PATHTYPE_DIRECTORY
            }
        }
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetContents(path: String): List<String> {
        val callback = staticCFunction<COpaquePointer?, CPointer<ByteVar>?, CPointer<ByteVar>?, SDL_EnumerationResult> { userdata, _, filename ->
            val list = userdata!!.asStableRef<MutableList<String>>().get()
            list += filename!!.toKString()
            SDL_EnumerationResult.SDL_ENUM_CONTINUE
        }
        val result = mutableListOf<String>()
        val resultStableRef = StableRef.create(result)
        if (SDL_EnumerateStorageDirectory(this.NativeStorage, path, callback, resultStableRef.asCPointer()).SDL_CheckError()) {
            resultStableRef.dispose()
            return result
        } else {
            resultStableRef.dispose()
            error("Couldn't enumerate directory: $path")
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun LoadAsset(path: String): ByteArray {
        memScoped {
            val length = this.alloc<ULongVar>()
            if (SDL_GetStorageFileSize(this@Content.NativeStorage, path, length.ptr).SDL_CheckError()) {
                if (length.value == 0UL) {
                    return ByteArray(0)
                }
                if (length.value <= Int.MAX_VALUE.toULong()) {
                    val data = ByteArray(length.value.toInt())
                    data.usePinned {
                        if (SDL_ReadStorageFile(this@Content.NativeStorage, path, it.addressOf(0), length.value).SDL_CheckError()) {
                            return data
                        }
                    }
                }
            }
        }
        error("Couldn't load file: $path")
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun LoadAssetAsync(path: String, callback: (LoadAssetAsyncResult) -> Unit) {
        val callbackStableRef = StableRef.create(callback).asCPointer()
        if (SDL_LoadFileAsync(path, this.NativeAsyncIOQueue, callbackStableRef).SDL_CheckError()) {
            return
        }
        error("Couldn't load file: $path")
    }

}
