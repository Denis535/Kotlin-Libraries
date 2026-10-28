package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.input.*

public fun Main(args: Array<String>) {
    ClientEngine2(Manifest("Example", "com.denis535", "example")).apply {
        this.OnStartCallback = {}
        this.OnStopCallback = {}

        this.Window.OnCursorEnterCallback = { event ->
//            println(event)
        }
        this.Window.OnCursorLeaveCallback = { event ->
//            println(event)
        }
        this.Window.OnKeyboardFocusCallback = { event ->
//            println(event)
        }

        this.Mouse!!.OnMoveCallback = { event ->
        }
        this.Mouse!!.OnButtonActionCallback = { event ->
        }
        this.Mouse!!.OnWheelScrollCallback = { event ->
        }

        this.Keyboard!!.OnKeyActionCallback = { event ->
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
