package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.utils.*

public fun Main(args: Array<String>) {
    ClientEngine2(Manifest("Example", "com.denis535", "example", null, "Denis535")).apply {
        this.Window = Window2(WindowDescription.Window("Example", IsResizable = true))
        this.Window!!.Show()
        this.Window!!.Raise()
        this.OnDrawCallback = {
            Utils.Delay(10U)
        }
        this.OnUpdateCallback = {
//            println("Frame: " + Frame.Number)
        }
        this.OnFixedUpdateCallback = {
//            println("FixedFrame: " + FixedFrame.Number)
        }
        this.OnMouseMoveCallback = { event ->
//            println(event.Cursor)
        }
        this.OnMouseButtonActionCallback = { event ->
//            println(event.Button)
        }
        this.OnMouseWheelScrollCallback = { event ->
//            println(event.Scroll.Y)
        }
        this.OnKeyboardKeyActionCallback = { event ->
            if (event.Key == KeyboardKey.Enter && this.Keyboard.IsKeyPressed(KeyboardKey.RightAlt)) {
                this.Window!!.IsFullScreen = !this.Window!!.IsFullScreen
            }
        }
    }.use {
        it.Run()
    }
}

private class ClientEngine2 : ClientEngine {

    public val Content: Content
    public val Storage: Storage

    public constructor(manifest: Manifest) : super(manifest) {
        this.Content = Content(null)
        this.Storage = Storage(manifest.Group, manifest.Name)
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

private class Window2 : Window {

    public constructor(description: WindowDescription) : super(description) {
    }

    public override fun close() {
        super.close()
    }

}
