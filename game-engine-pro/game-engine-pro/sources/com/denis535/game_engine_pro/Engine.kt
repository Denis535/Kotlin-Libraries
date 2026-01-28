package com.denis535.game_engine_pro

import com.denis535.sdl.*
import kotlinx.cinterop.*

public abstract class Engine : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public var IsRunning: Boolean = false
        get() {
            check(!this.IsClosed)
            return field
        }
        protected set(value) {
            check(!this.IsClosed)
            field = value
        }

    public var OnStartCallback: (() -> Unit)? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            if (field != null) {
                require(value == null)
            } else {
                require(value != null)
            }
            field = value
        }
    public var OnStopCallback: (() -> Unit)? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            if (field != null) {
                require(value == null)
            } else {
                require(value != null)
            }
            field = value
        }

    public var OnUpdateCallback: (() -> Unit)? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            if (field != null) {
                require(value == null)
            } else {
                require(value != null)
            }
            field = value
        }
    public var OnFixedUpdateCallback: (() -> Unit)? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            if (field != null) {
                require(value == null)
            } else {
                require(value != null)
            }
            field = value
        }

    @OptIn(ExperimentalForeignApi::class)
    internal constructor(manifest: Manifest) {
        SDL_SetHint(SDL_HINT_STORAGE_USER_DRIVER, "generic").SDL_CheckError()
        SDL_Init(0U).SDL_CheckError()
        SDL_SetAppMetadata(manifest.Title, manifest.Version, manifest.ID).SDL_CheckError()
        SDL_SetAppMetadataProperty(SDL_PROP_APP_METADATA_TYPE_STRING, "game").SDL_CheckError()
        SDL_SetAppMetadataProperty(SDL_PROP_APP_METADATA_CREATOR_STRING, manifest.Creator).SDL_CheckError()
        SDL_SetAppMetadataProperty(SDL_PROP_APP_METADATA_URL_STRING, manifest.Url).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        SDL_Quit().SDL_CheckError()
        this.IsClosed = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Run(fixedTimeStep: Float = 1.0f / 20.0f) {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.IsRunning = true
        this.OnStartCallback?.invoke()
        while (this.IsRunning) {
            this.ProcessFrame(fixedTimeStep)
        }
        this.OnStopCallback?.invoke()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ProcessFrame(fixedTimeStep: Float) {
        check(!this.IsClosed)
        val startTime = SDL_GetTicks().SDL_CheckError()
        run {
            this.ProcessEvents()
            this.ProcessFixedFrame(fixedTimeStep)
            this.OnUpdateCallback?.invoke()
            if (this is ClientEngine) {
                this.OnDrawCallback?.invoke()
            }
        }
        val endTime = SDL_GetTicks().SDL_CheckError()
        val deltaTime = (endTime - startTime).toFloat() / 1000f
        Frame.Number++
        Frame.Time += deltaTime
        Frame.TimeStep = deltaTime
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ProcessEvents() {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            while (SDL_PollEvent(event.ptr).SDL_CheckError()) {
                this@Engine.ProcessEvent(event.ptr)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    internal open fun ProcessEvent(event: CPointer<SDL_Event>) {
        check(!this.IsClosed)
        when (event.pointed.type) {
            SDL_EVENT_QUIT -> {
                this.IsRunning = false
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ProcessFixedFrame(fixedTimeStep: Float) {
        check(!this.IsClosed)
        if (FixedFrame.Number == 0U) {
            this.OnFixedUpdateCallback?.invoke()
            FixedFrame.Number++
            FixedFrame.TimeStep = fixedTimeStep
        } else {
            while (FixedFrame.Time <= Frame.Time) {
                this.OnFixedUpdateCallback?.invoke()
                FixedFrame.Number++
                FixedFrame.TimeStep = fixedTimeStep
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun RequestQuit() {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            event.type = SDL_EVENT_QUIT
            SDL_PushEvent(event.ptr).SDL_CheckError()
        }
    }

}

public class Manifest(
    public val Title: String,
    public val Group: String,
    public val Name: String,
    public val Version: String? = null,
    public val Creator: String? = null,
    public val Url: String? = null,
) {
    public val ID: String
        get() = "${this.Group}.${this.Name}"
}

public object Frame {

    public var Number: UInt = 0U
        internal set

    public var Time: Float = 0.0f
        internal set

    public var TimeStep: Float = 0.0f
        internal set

    public val Fps: Float?
        get() = if (this.TimeStep > 0.0f) 1.0f / this.TimeStep else null

    public override fun toString(): String {
        return "Frame: ${this.Number}"
    }

}

public object FixedFrame {

    public var Number: UInt = 0U
        internal set

    public val Time: Float
        get() = this.Number.toFloat() * this.TimeStep

    public var TimeStep: Float = 0.0f
        internal set

    public override fun toString(): String {
        return "FixedFrame: ${this.Number}"
    }

}
