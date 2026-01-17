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
        val time = Time()
        this.IsRunning = true
        this.OnStart(time)
        while (true) {
            val startTime = SDL_GetTicks().also { SDL.ThrowErrorIfNeeded() }
            this.ProcessFrame(time, fixedDeltaTime)
            if (!this.IsRunning) {
                break
            }
            val endTime = SDL_GetTicks().also { SDL.ThrowErrorIfNeeded() }
            val deltaTime = (endTime - startTime).toFloat() / 1000f
            time.Number++
            time.Time += deltaTime
            time.DeltaTime = deltaTime
        }
        this.OnStop(time)
    }

    @OptIn(ExperimentalForeignApi::class)
    internal open fun ProcessFrame(time: Time, fixedDeltaTime: Float) {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            while (SDL_PollEvent(event.ptr).also { SDL.ThrowErrorIfNeeded() }) {
                this@Engine.ProcessEvent(time, event.ptr)
            }
        }
        if (time.Fixed.Number == 0) {
            this.OnFixedUpdate(time)
            time.Fixed.Number++
            time.Fixed.DeltaTime = fixedDeltaTime
        } else {
            while (time.Fixed.Time <= time.Time) {
                this.OnFixedUpdate(time)
                time.Fixed.Number++
                time.Fixed.DeltaTime = fixedDeltaTime
            }
        }
        this.OnUpdate(time)
    }

    @OptIn(ExperimentalForeignApi::class)
    internal open fun ProcessEvent(time: Time, event: CPointer<SDL_Event>) {
        check(!this.IsClosed)
        when (event.pointed.type) {
            SDL_EVENT_QUIT -> {
                this.IsRunning = false
            }
        }
    }

    protected abstract fun OnStart(time: Time)
    protected abstract fun OnStop(time: Time)

    protected abstract fun OnFixedUpdate(time: Time)
    protected abstract fun OnUpdate(time: Time)

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

public class Time {

    public val Fixed: FixedTime = FixedTime()

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
        return "Time(Number=${this.Number}, Time=${this.Time})"
    }

}

public class FixedTime {

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
        return "FixedTime(Number=${this.Number}, Time=${this.Time})"
    }

}
