package com.denis535.game_engine_pro.utils

import cnames.structs.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Content : AutoCloseable {

    @OptIn(ExperimentalForeignApi::class)
    private val NativeStorage: CPointer<SDL_Storage>

    @OptIn(ExperimentalForeignApi::class)
    public val IsReady: Boolean
        get() = SDL_StorageReady(this.NativeStorage).SDL_CheckError()

    @OptIn(ExperimentalForeignApi::class)
    public constructor(path: String, isUnsafe: Boolean = false) {
        if (isUnsafe) {
            this.NativeStorage = SDL_OpenFileStorage(path).SDL_CheckError()!!
        } else {
            val properties = SDL_CreateProperties().SDL_CheckError()
            try {
                this.NativeStorage = SDL_OpenTitleStorage(path, properties).SDL_CheckError()!!
            } finally {
                SDL_DestroyProperties(properties).SDL_CheckError()
            }
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

    @OptIn(ExperimentalForeignApi::class)
    public fun Load(path: String): ByteArray? {
        memScoped {
            val length = this.alloc<ULongVar>()
            if (SDL_GetStorageFileSize(this@Content.NativeStorage, path, length.ptr).SDL_CheckError()) {
                val data = ByteArray(length.value.toInt())
                data.usePinned {
                    if (SDL_ReadStorageFile(this@Content.NativeStorage, path, it.addressOf(0), length.value).SDL_CheckError()) {
                        return data
                    }
                }
            }
        }
        return null
    }

    public fun LoadText(path: String): String? {
        return this.Load(path)?.decodeToString()
    }

}
