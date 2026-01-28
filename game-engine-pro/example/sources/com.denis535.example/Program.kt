package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.utils.*

public fun Main(args: Array<String>) {
    ClientEngine2().apply {
        this.OnDrawCallback = {
            Utils.Delay(10U)
        }
        this.OnKeyboardKeyActionCallback = { event ->
            if (event.Key == KeyboardKey.Enter && this.Keyboard.IsKeyPressed(KeyboardKey.RightAlt)) {
                this.Window.IsFullScreen = !this.Window.IsFullScreen
            }
        }
    }.use {
        it.Run()
    }
}

private class ClientEngine2 : ClientEngine {

    public constructor() : super(Description("Example", "com.denis535", "example", null, "Denis535"), { Window2() }) {
    }

    public override fun close() {
        super.close()
    }

}

private class Window2 : Window {

    public constructor() : super(Description.Window("Example", IsResizable = true)) {
    }

    public override fun close() {
        super.close()
    }

}
