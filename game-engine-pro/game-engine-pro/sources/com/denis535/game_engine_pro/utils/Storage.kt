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
    public fun GetEntityList(path: String): List<String> {
        val callback = staticCFunction<COpaquePointer?, CPointer<ByteVar>?, CPointer<ByteVar>?, SDL_EnumerationResult> { userdata, _, filename ->
            val result = userdata!!.asStableRef<MutableList<String>>().get()
            result += filename!!.toKString()
            SDL_EnumerationResult.SDL_ENUM_CONTINUE
        }
        val result = mutableListOf<String>()
        val resultStableRef = StableRef.create(result)
        try {
            SDL_EnumerateStorageDirectory(this.NativeStorage, path, callback, resultStableRef.asCPointer()).SDL_CheckError()
        } finally {
            resultStableRef.dispose()
        }
        return result
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun LoadEntity(path: String): ByteArray? {
        while (!SDL_StorageReady(this.NativeStorage).SDL_CheckError()) {
            SDL_Delay(10U).SDL_CheckError()
        }
        val length = memScoped {
            val length = this.alloc<ULongVar>()
            if (!SDL_GetStorageFileSize(this@Storage.NativeStorage, path, length.ptr).SDL_CheckError()) {
                return null
            }
            length.value
        }
        if (length == 0UL) {
            return null
        }
        if (length > Int.MAX_VALUE.toULong()) {
            return null
        }
        return ByteArray(length.toInt()).apply {
            this.usePinned {
                if (!SDL_ReadStorageFile(this@Storage.NativeStorage, path, it.addressOf(0), length).SDL_CheckError()) {
                    return null
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun SaveEntity(path: String, data: ByteArray) {
        while (!SDL_StorageReady(this.NativeStorage).SDL_CheckError()) {
            SDL_Delay(10U).SDL_CheckError()
        }
        data.usePinned {
            SDL_WriteStorageFile(this@Storage.NativeStorage, path, it.addressOf(0), it.get().size.toULong()).SDL_CheckError()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun DeleteEntity(path: String) {
        while (!SDL_StorageReady(this.NativeStorage).SDL_CheckError()) {
            SDL_Delay(10U).SDL_CheckError()
        }
        SDL_RemoveStoragePath(this.NativeStorage, path).SDL_CheckError()
    }

}
