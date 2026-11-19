package com.denis535.game_engine_pro.storage

import cnames.structs.*
import kotlinx.cinterop.*
import sdl.*

public class Content : AutoCloseable {

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
                this@Content.Process(outcome)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun Process(outcome: SDL_AsyncIOOutcome) {
        if (outcome.type == SDL_AsyncIOTaskType.SDL_ASYNCIO_TASK_READ) {
            val callbackStableRef = outcome.userdata!!.asStableRef<(AssetAsync) -> Unit>()
            run {
                if (outcome.result == SDL_AsyncIOResult.SDL_ASYNCIO_COMPLETE) {
                    val data = outcome.buffer!!.readBytes(outcome.bytes_transferred.toInt())
                    callbackStableRef.get().invoke(AssetAsync.Completed(data))
                    SDL_free(outcome.buffer).SDL_CheckError()
                } else if (outcome.result == SDL_AsyncIOResult.SDL_ASYNCIO_FAILURE) {
                    val error = SDL.GetError() ?: "unknown error"
                    callbackStableRef.get().invoke(AssetAsync.Faulted(error))
                    SDL_free(outcome.buffer).SDL_CheckError()
                } else if (outcome.result == SDL_AsyncIOResult.SDL_ASYNCIO_CANCELED) {
                    callbackStableRef.get().invoke(AssetAsync.Canceled())
                    SDL_free(outcome.buffer).SDL_CheckError()
                }
            }
            callbackStableRef.dispose()
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
        val callback = staticCFunction<COpaquePointer?, CPointer<ByteVar>?, CPointer<ByteVar>?, SDL_EnumerationResult> { resultStableRef, _, item ->
            val result = resultStableRef!!.asStableRef<MutableList<String>>().get()
            result += item!!.toKString()
            SDL_EnumerationResult.SDL_ENUM_CONTINUE
        }
        val result = mutableListOf<String>()
        val resultStableRef = StableRef.create(result)
        if (SDL_EnumerateStorageDirectory(this.NativeStorage, path, callback, resultStableRef.asCPointer()).SDL_CheckError()) {
            resultStableRef.dispose()
            return result
        } else {
            resultStableRef.dispose()
            error("Couldn't get contents: $path")
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun LoadAsset(path: String): Asset {
        memScoped {
            val length = this.alloc<ULongVar>()
            if (SDL_GetStorageFileSize(this@Content.NativeStorage, path, length.ptr).SDL_CheckError()) {
                if (length.value == 0UL) {
                    return Asset(path, ByteArray(0))
                }
                if (length.value <= Int.MAX_VALUE.toULong()) {
                    val data = ByteArray(length.value.toInt())
                    data.usePinned {
                        if (SDL_ReadStorageFile(this@Content.NativeStorage, path, it.addressOf(0), length.value).SDL_CheckError()) {
                            return Asset(path, data)
                        }
                    }
                }
            }
        }
        error("Couldn't load asset: $path")
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun LoadAssetAsync(path: String, callback: (AssetAsync) -> Unit) {
        val callbackStableRef = StableRef.create(callback).asCPointer()
        if (SDL_LoadFileAsync(path, this.NativeAsyncIOQueue, callbackStableRef).SDL_CheckError()) {
            return
        }
        error("Couldn't load asset: $path")
    }

}

public data class Asset(public val Path: String, public val Data: ByteArray)
public sealed class AssetAsync {
    public data class Completed(public val Path: String, public val Data: ByteArray) : AssetAsync()
    public data class Faulted(public val Path: String, public val Error: String) : AssetAsync()
    public data class Canceled(public val Path: String) : AssetAsync()
}
