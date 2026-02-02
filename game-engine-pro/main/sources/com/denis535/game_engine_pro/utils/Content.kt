package com.denis535.game_engine_pro.utils

import cnames.structs.*
import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public class Content : AutoCloseable {

    @OptIn(ExperimentalForeignApi::class)
    private val NativeStorage: CPointer<SDL_Storage>

//    @OptIn(ExperimentalForeignApi::class)
//    private val NativeAsyncIOQueue: CPointer<SDL_AsyncIOQueue>

    @OptIn(ExperimentalForeignApi::class)
    public val IsReady: Boolean
        get() = SDL_StorageReady(this.NativeStorage).SDL_CheckError()

    @OptIn(ExperimentalForeignApi::class)
    public constructor(path: String?) {
        val properties = SDL_CreateProperties().SDL_CheckError()
        try {
            this.NativeStorage = SDL_OpenTitleStorage(path, properties).SDL_CheckError()!!
        } finally {
            SDL_DestroyProperties(properties).SDL_CheckError()
        }
//        this.NativeAsyncIOQueue = SDL_CreateAsyncIOQueue().SDL_CheckError()!!
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
//        SDL_DestroyAsyncIOQueue(this.NativeAsyncIOQueue).SDL_CheckError()
        SDL_CloseStorage(this.NativeStorage).SDL_CheckError()
    }

//    @OptIn(ExperimentalForeignApi::class)
//    internal fun Process() {
//        memScoped {
//            val outcome = this.alloc<SDL_AsyncIOOutcome>()
//            while (SDL_GetAsyncIOResult(this@Content.NativeAsyncIOQueue, outcome.ptr).SDL_CheckError()) {
//                val callbackStableRef = outcome.userdata!!.asStableRef<(ByteArray) -> Unit>()
//                try {
//                    if (outcome.result == SDL_AsyncIOResult.SDL_ASYNCIO_COMPLETE) {
//                        val data = outcome.buffer!!.readBytes(outcome.bytes_transferred.toInt())
//                        callbackStableRef.get().invoke(data)
//                    } else if (outcome.result == SDL_AsyncIOResult.SDL_ASYNCIO_FAILURE) {
//                        SDL.CheckError()
//                    } else if (outcome.result == SDL_AsyncIOResult.SDL_ASYNCIO_CANCELED) {
//
//                    }
//                } finally {
//                    SDL_free(outcome.buffer).SDL_CheckError()
//                    callbackStableRef.dispose()
//                }
//            }
//        }
//    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsFile(path: String): Boolean {
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
    public fun GetDirectoryContents(path: String): List<String> {
        val callback = staticCFunction<COpaquePointer?, CPointer<ByteVar>?, CPointer<ByteVar>?, SDL_EnumerationResult> { userdata, _, filename ->
            val list = userdata!!.asStableRef<MutableList<String>>().get()
            list += filename!!.toKString()
            SDL_EnumerationResult.SDL_ENUM_CONTINUE
        }
        val result = mutableListOf<String>()
        val resultStableRef = StableRef.create(result)
        try {
            if (SDL_EnumerateStorageDirectory(this.NativeStorage, path, callback, resultStableRef.asCPointer()).SDL_CheckError()) {
                return result
            }
            error("Couldn't enumerate directory: $path")
        } finally {
            resultStableRef.dispose()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Load(path: String): ByteArray {
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

//    @OptIn(ExperimentalForeignApi::class)
//    public fun LoadAsync(path: String, callback: (ByteArray) -> Unit) {
//        val callbackStableRef = StableRef.create(callback).asCPointer().reinterpret<COpaquePointerVar>()
//        if (SDL_LoadFileAsync(path, this.NativeAsyncIOQueue, callbackStableRef).SDL_CheckError()) {
//            return
//        }
//        error("Couldn't load file: $path")
//    }

}
