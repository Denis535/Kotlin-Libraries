package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.io.*
import sdl.*

public fun Main(args: Array<String>) {
    println(SDL.Version)
    println(SDL.VersionCompiled)
    ClientEngine(Manifest("Example", "com.denis535", "example"), { Window("Example") }).apply {
        this.VideoRenderer = null
        this.AudioRenderer = null
        this.Touchscreen = Touchscreen()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
        this.Gamepads = listOf(Gamepad(), Gamepad())
        this.Content = Content(null)
        this.Storage = Storage(this.Manifest.Group, this.Manifest.Name)

        this.OnStartCallback = {}
        this.OnStopCallback = {}

        this.Window.OnMouseFocusCallback = { event ->
//            println(event)
        }
        this.Window.OnInputFocusCallback = { event ->
//            println(event)
        }

        this.Mouse!!.OnMoveCallback = { event ->
        }
        this.Mouse!!.OnButtonCallback = { event ->
        }
        this.Mouse!!.OnScrollCallback = { event ->
        }

        this.Keyboard!!.OnKeyCallback = { event ->
            if (event.Key == KeyboardKey.Enter && event.IsPressed && !event.IsRepeated && this.Keyboard!!.IsKeyPressed(KeyboardKey.RightAlt)) {
                this.Window.IsFullScreen = !this.Window.IsFullScreen
            }
        }

        this.OnFixedUpdateCallback = {
            Utils.Delay(10U)
//            println("FixedFrame: " + FixedFrame.Number)
        }
        this.OnUpdateCallback = {
            Utils.Delay(10U)
//            println("Frame: " + Frame.Number)
        }
    }.use {
        it.Window.Show()
        it.Window.Raise()
        it.Run()
    }
}
