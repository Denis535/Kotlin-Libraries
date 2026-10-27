package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.storage.*

public fun Main(args: Array<String>) {
    ClientEngine2().apply {
        this.OnStartCallback = {}
        this.OnStopCallback = {}

        this.Mouse.OnMoveCallback = { event ->
//            println(event.Cursor)
        }
        this.Mouse.OnButtonActionCallback = { event ->
//            println(event.Button)
        }
        this.Mouse.OnWheelScrollCallback = { event ->
//            println(event.Scroll.Y)
        }
        this.Keyboard.OnKeyActionCallback = { event ->
            if (event.Key == KeyboardKey.Enter && this.Keyboard.IsKeyPressed(KeyboardKey.RightAlt)) {
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

    public val Content: Content
    public val Storage: Storage

    public constructor() : super(
        Manifest("Example", "com.denis535", "example", null, "Denis535"),
        WindowDescription.Window("Example", IsResizable = false)) {
        this.Content = Content(null)
        this.Storage = Storage(this.Manifest.Group, this.Manifest.Name)
        this.Window.Show()
        this.Window.Raise()
    }

    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Storage.close()
        this.Content.close()
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
        this.Content.Process()
    }

    protected override fun OnFrameEnd() {
        super.OnFrameEnd()
    }

}
