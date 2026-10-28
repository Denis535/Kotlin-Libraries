package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.storage.*

public fun Main(args: Array<String>) {
    ClientEngine2().apply {
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

        this.Mouse.OnMoveCallback = { event ->
        }
        this.Mouse.OnButtonActionCallback = { event ->
        }
        this.Mouse.OnWheelScrollCallback = { event ->
        }
        this.Keyboard.OnKeyActionCallback = { event ->
            if (event.Key == KeyboardKey.Enter && event.IsPressed && !event.IsRepeated && this.Keyboard.IsKeyPressed(KeyboardKey.RightAlt)) {
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
        it.Run()
    }
}

private class ClientEngine2 : ClientEngine {

    public constructor() : super(Manifest("Example", "com.denis535", "example", null, "Denis535")) {
        this.Window = Window("Example", isResizable = true)
        this.Cursor = Cursor()
        this.Touchscreen = Touchscreen()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
        this.Gamepads = listOf(Gamepad(), Gamepad(), Gamepad(), Gamepad())
        this.Content = Content(null)
        this.Storage = Storage(this.Manifest.Group, this.Manifest.Name)

        this.Window.Show()
        this.Window.Raise()
    }

    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Storage!!.close()
        this.Content!!.close()
        this.Gamepads.asReversed().forEach { it.close() }
        this.Keyboard.close()
        this.Mouse.close()
        this.Touchscreen.close()
        this.Cursor.close()
        this.Window.close()
        super.close()
    }

    protected override fun OnStart() {
        super.OnStart()
    }

    protected override fun OnStop() {
        super.OnStop()
    }

    protected override fun OnFrameBegin() {
        super.OnFrameBegin()
    }

    protected override fun OnFrameEnd() {
        super.OnFrameEnd()
    }

}
