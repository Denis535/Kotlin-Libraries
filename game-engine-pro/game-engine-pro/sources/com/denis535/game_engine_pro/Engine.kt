package com.denis535.game_engine_pro

import com.denis535.sdl.*
import kotlinx.cinterop.*

public abstract class Engine : AutoCloseable {
    public class Description(public val Id: String?, public val Name: String? = null, public val Version: String? = null, public val Creator: String? = null)

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

    @OptIn(ExperimentalForeignApi::class)
    internal constructor(description: Description) {
        SDL_Init(0U).SDL_CheckError()
        SDL_SetAppMetadata(description.Name, description.Version, description.Id).SDL_CheckError()
        SDL_SetAppMetadataProperty(SDL_PROP_APP_METADATA_CREATOR_STRING, description.Creator).SDL_CheckError()
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
        this.OnStart()
        while (this.IsRunning) {
            this.ProcessFrame(fixedTimeStep)
        }
        this.OnStop()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ProcessFrame(fixedTimeStep: Float) {
        check(!this.IsClosed)
        val startTime = SDL_GetTicks().SDL_CheckError()
        run {
            memScoped {
                val event = this.alloc<SDL_Event>()
                while (SDL_PollEvent(event.ptr).SDL_CheckError()) {
                    this@Engine.ProcessEvent(event.ptr)
                }
            }
            this.ProcessFixedFrame(fixedTimeStep)
            this.OnUpdate()
            if (this is ClientEngine) {
                this.OnDrawInternal()
            }
        }
        val endTime = SDL_GetTicks().SDL_CheckError()
        val deltaTime = (endTime - startTime).toFloat() / 1000f
        Frame.Number++
        Frame.Time += deltaTime
        Frame.TimeStep = deltaTime
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
            this.OnFixedUpdate()
            FixedFrame.Number++
            FixedFrame.TimeStep = fixedTimeStep
        } else {
            while (FixedFrame.Time <= Frame.Time) {
                this.OnFixedUpdate()
                FixedFrame.Number++
                FixedFrame.TimeStep = fixedTimeStep
            }
        }
    }

    protected abstract fun OnStart()
    protected abstract fun OnStop()

    protected abstract fun OnUpdate()
    protected abstract fun OnFixedUpdate()

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
