package com.denis535.game_engine_pro.utils

import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Storage : AutoCloseable {

    @OptIn(ExperimentalForeignApi::class)
    private val NativeStorage: CPointer<cnames.structs.SDL_Storage>

    @OptIn(ExperimentalForeignApi::class)
    public val IsReady: Boolean
        get() = SDL_StorageReady(this.NativeStorage).SDL_CheckError()

    @OptIn(ExperimentalForeignApi::class)
    public constructor(group: String, name: String) {
        val properties = SDL_CreateProperties().SDL_CheckError()
        try {
            this.NativeStorage = SDL_OpenUserStorage(group, name, properties).SDL_CheckError()!!
        } finally {
            SDL_DestroyProperties(properties).SDL_CheckError()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        SDL_CloseStorage(this.NativeStorage).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsFile(path: String): Boolean {
        memScoped {
            val info = this.alloc<SDL_PathInfo>()
            if (SDL_GetStoragePathInfo(this@Storage.NativeStorage, path, info.ptr).SDL_CheckError()) {
                return info.type == SDL_PathType.SDL_PATHTYPE_FILE
            }
        }
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsDirectory(path: String): Boolean {
        memScoped {
            val info = this.alloc<SDL_PathInfo>()
            if (SDL_GetStoragePathInfo(this@Storage.NativeStorage, path, info.ptr).SDL_CheckError()) {
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
            if (SDL_GetStorageFileSize(this@Storage.NativeStorage, path, length.ptr).SDL_CheckError()) {
                if (length.value >= 0U && length.value < UInt.MAX_VALUE) {
                    val data = ByteArray(length.value.toInt())
                    data.usePinned {
                        if (SDL_ReadStorageFile(this@Storage.NativeStorage, path, it.addressOf(0), length.value).SDL_CheckError()) {
                            return data
                        }
                    }
                }
            }
        }
        error("Couldn't load file: $path")
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Save(path: String, data: ByteArray) {
        data.usePinned {
            if (SDL_WriteStorageFile(this@Storage.NativeStorage, path, it.addressOf(0), it.get().size.toULong()).SDL_CheckError()) {
                return
            }
        }
        error("Couldn't save file: $path")
    }

    public fun LoadText(path: String): String {
        return this.Load(path).decodeToString()
    }

    public fun SaveText(path: String, text: String) {
        this.Save(path, text.encodeToByteArray())
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Delete(path: String) {
        if (SDL_RemoveStoragePath(this.NativeStorage, path).SDL_CheckError()) {
            return
        }
        error("Couldn't delete file or directory: $path")
    }

}
