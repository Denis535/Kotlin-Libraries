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

    public constructor(manifest: Manifest) : super(manifest) {
    }

    public override fun close() {
        super.close()
    }

}

private class Window2 : Window {

    public constructor(description: WindowDescription) : super(description) {
    }

    public override fun close() {
        super.close()
    }

}
