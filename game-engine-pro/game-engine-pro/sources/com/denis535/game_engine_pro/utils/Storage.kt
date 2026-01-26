package com.denis535.game_engine_pro.utils

import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Storage : AutoCloseable {

    @OptIn(ExperimentalForeignApi::class)
    private val NativeStorage: CPointer<cnames.structs.SDL_Storage>

    @OptIn(ExperimentalForeignApi::class)
    public constructor(group: String, name: String) {
        val properties = SDL_CreateProperties().SDL_CheckError()
        this.NativeStorage = SDL_OpenUserStorage(group, name, properties).SDL_CheckError()!!
        SDL_DestroyProperties(properties).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        SDL_CloseStorage(this.NativeStorage).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun CheckEntity(path: String): Boolean {
        while (!SDL_StorageReady(this.NativeStorage).SDL_CheckError()) {
            SDL_Delay(10U).SDL_CheckError()
        }
        memScoped {
            val info = this.alloc<SDL_PathInfo>()
            if (SDL_GetStoragePathInfo(this@Storage.NativeStorage, path, info.ptr).SDL_CheckError()) {
                return info.type == SDL_PathType.SDL_PATHTYPE_FILE
            } else {
                return false
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun LoadEntity(path: String): ByteArray? {
        while (!SDL_StorageReady(this.NativeStorage).SDL_CheckError()) {
            SDL_Delay(10U).SDL_CheckError()
        }
        memScoped {
            val length = this.alloc<ULongVar>()
            if (SDL_GetStorageFileSize(this@Storage.NativeStorage, path, length.ptr).SDL_CheckError()) {
                val content = ByteArray(length.value.toInt())
                content.usePinned {
                    if (SDL_ReadStorageFile(this@Storage.NativeStorage, path, it.addressOf(0), length.value).SDL_CheckError()) {
                        return content
                    }
                }
            }
            return null
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun SaveEntity(path: String, content: ByteArray): Boolean {
        while (!SDL_StorageReady(this.NativeStorage).SDL_CheckError()) {
            SDL_Delay(10U).SDL_CheckError()
        }
        content.usePinned {
            if (SDL_WriteStorageFile(this@Storage.NativeStorage, path, it.addressOf(0), it.get().size.toULong()).SDL_CheckError()) {
                return true
            }
        }
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun DeleteEntity(path: String): Boolean {
        while (!SDL_StorageReady(this.NativeStorage).SDL_CheckError()) {
            SDL_Delay(10U).SDL_CheckError()
        }
        if (SDL_RemoveStoragePath(this.NativeStorage, path).SDL_CheckError()) {
            return true
        }
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetDirectoryContents(path: String): List<String>? {
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
        } finally {
            resultStableRef.dispose()
        }
        return null
    }

}
