package com.denis535.game_engine_pro

import com.denis535.sdl.*
import kotlinx.cinterop.*

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

    public val Time: Time = Time()
        get() {
            check(!this.IsClosed)
            return field
        }

    @OptIn(ExperimentalForeignApi::class)
    internal constructor(manifest: Manifest) {
        check(SDL_WasInit(0U).also { SDL.ThrowErrorIfNeeded() } == 0U)
        SDL_Init(0U).also { SDL.ThrowErrorIfNeeded() }
        SDL_SetAppMetadata(manifest.Name, manifest.Version, manifest.Id).also { SDL.ThrowErrorIfNeeded() }
        SDL_SetAppMetadataProperty(SDL_PROP_APP_METADATA_CREATOR_STRING, manifest.Creator).also { SDL.ThrowErrorIfNeeded() }
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        SDL_Quit().also { SDL.ThrowErrorIfNeeded() }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Run(fixedDeltaTime: Float = 1.0f / 20.0f) {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.IsRunning = true
        this.OnStart()
        while (this.IsRunning) {
            val startTime = SDL_GetTicks().also { SDL.ThrowErrorIfNeeded() }
            this.ProcessFrame(fixedDeltaTime)
            if (this.IsRunning) {
                val endTime = SDL_GetTicks().also { SDL.ThrowErrorIfNeeded() }
                val deltaTime = (endTime - startTime).toFloat() / 1000f
                this.Time.Number++
                this.Time.Time += deltaTime
                this.Time.DeltaTime = deltaTime
            }
        }
        this.OnStop()
    }

    @OptIn(ExperimentalForeignApi::class)
    internal open fun ProcessFrame(fixedDeltaTime: Float) {
        check(!this.IsClosed)
        this.ProcessEvents()
        if (this.Time.Fixed.Number == 0) {
            this.OnFixedUpdate()
            this.Time.Fixed.Number++
            this.Time.Fixed.DeltaTime = fixedDeltaTime
        } else {
            while (this.Time.Fixed.Time <= this.Time.Time) {
                this.OnFixedUpdate()
                this.Time.Fixed.Number++
                this.Time.Fixed.DeltaTime = fixedDeltaTime
            }
        }
        this.OnUpdate()
    }

    @OptIn(ExperimentalForeignApi::class)
    internal open fun ProcessEvents() {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            while (SDL_PollEvent(event.ptr).also { SDL.ThrowErrorIfNeeded() }) {
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

    protected abstract fun OnStart()
    protected abstract fun OnStop()

    protected abstract fun OnFixedUpdate()
    protected abstract fun OnUpdate()

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
