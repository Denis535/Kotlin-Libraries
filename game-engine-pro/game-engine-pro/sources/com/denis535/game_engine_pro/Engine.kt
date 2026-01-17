package com.denis535.game_engine_pro

import kotlinx.cinterop.*
import com.denis535.sdl.*

public abstract class Engine : AutoCloseable {
    public class Manifest(public val Id: String?, public val Name: String? = null, public val Version: String? = null, public val Creator: String? = null)

    @OptIn(ExperimentalForeignApi::class)
    public val IsClosed: Boolean
        get() {
            return SDL_WasInit(0U).also { SDL.ThrowErrorIfNeeded() } == 0U
        }

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
    internal constructor() {
        check(SDL_WasInit(0U).also { SDL.ThrowErrorIfNeeded() } == 0U)
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Run(fixedDeltaTime: Float = 1.0f / 20.0f) {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.IsRunning = true
        val frame = Frame()
        this.OnStart(frame)
        while (true) {
            val startTime = SDL_GetTicks().also { SDL.ThrowErrorIfNeeded() }
            this.ProcessFrame(frame, fixedDeltaTime)
            if (!this.IsRunning) {
                break
            }
            val endTime = SDL_GetTicks().also { SDL.ThrowErrorIfNeeded() }
            val deltaTime = (endTime - startTime).toFloat() / 1000f
            frame.Number++
            frame.Time += deltaTime
            frame.DeltaTime = deltaTime
        }
        this.OnStop(frame)
    }

    @OptIn(ExperimentalForeignApi::class)
    internal open fun ProcessFrame(frame: Frame, fixedDeltaTime: Float) {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            while (SDL_PollEvent(event.ptr).also { SDL.ThrowErrorIfNeeded() }) {
                this@Engine.ProcessEvent(frame, event.ptr)
            }
        }
        if (frame.Fixed.Number == 0) {
            this.OnFixedUpdate(frame)
            frame.Fixed.Number++
            frame.Fixed.DeltaTime = fixedDeltaTime
        } else {
            while (frame.Fixed.Time <= frame.Time) {
                this.OnFixedUpdate(frame)
                frame.Fixed.Number++
                frame.Fixed.DeltaTime = fixedDeltaTime
            }
        }
        this.OnUpdate(frame)
    }

    @OptIn(ExperimentalForeignApi::class)
    internal open fun ProcessEvent(frame: Frame, event: CPointer<SDL_Event>) {
        check(!this.IsClosed)
        when (event.pointed.type) {
            SDL_EVENT_QUIT -> {
                this.IsRunning = false
            }
        }
    }

    protected abstract fun OnStart(frame: Frame)
    protected abstract fun OnStop(frame: Frame)

    protected abstract fun OnFixedUpdate(frame: Frame)
    protected abstract fun OnUpdate(frame: Frame)

    @OptIn(ExperimentalForeignApi::class)
    public fun RequestQuit() {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            event.type = SDL_EVENT_QUIT
            SDL_PushEvent(event.ptr).also { SDL.ThrowErrorIfNeeded() }
        }
    }

}

public class Frame {

    public val Fixed: FixedFrame = FixedFrame()

    public var Number: Int = 0
        internal set

    public var Time: Float = 0.0f
        internal set

    public var DeltaTime: Float = 0.0f
        internal set

    public val Fps: Float
        get() {
            return if (this.DeltaTime > 0.0f) 1.0f / this.DeltaTime else 0.0f
        }

    internal constructor()

    public override fun toString(): String {
        return "Frame(Number=${this.Number}, Time=${this.Time})"
    }

}

public class FixedFrame {

    public var Number: Int = 0
        internal set

    public val Time: Float
        get() {
            return this.Number * this.DeltaTime
        }

    public var DeltaTime: Float = 0.0f
        internal set

    internal constructor()

    public override fun toString(): String {
        return "FixedFrame(Number=${this.Number}, Time=${this.Time})"
    }

}
