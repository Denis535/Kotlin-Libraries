package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.utils.*

public fun Main(args: Array<String>) {
    ClientEngine2().use {
        it.Run()
    }
}

private class ClientEngine2 : ClientEngine {

    public constructor() : super(Description("Example"), { Window2() }) {
    }

    public override fun close() {
        super.close()
    }

    protected override fun OnStart() {
    }

    protected override fun OnStop() {
    }

    protected override fun OnDraw() {
        Utils.Delay(10U)
    }

    protected override fun OnUpdate() {
    }

    protected override fun OnFixedUpdate() {
    }

    protected override fun OnMouseFocus(event: MouseFocusEvent) {
    }

    protected override fun OnKeyboardFocus(event: KeyboardFocusEvent) {
    }

    protected override fun OnTextInput(event: TextInputEvent) {
    }

    protected override fun OnMouseMove(event: MouseMoveEvent) {
    }

    protected override fun OnMouseButtonAction(event: MouseButtonActionEvent) {
    }

    protected override fun OnMouseWheelScroll(event: MouseWheelScrollEvent) {
    }

    protected override fun OnKeyboardKeyAction(event: KeyboardKeyActionEvent) {
        if (event.Key == KeyboardKey.Enter && this.Keyboard.IsKeyPressed(KeyboardKey.RightAlt)) {
            this.Window.IsFullScreen = !this.Window.IsFullScreen
        }
    }

    protected override fun OnGamepadButtonAction(event: GamepadButtonActionEvent) {
    }

    protected override fun OnGamepadAxisChange(event: GamepadAxisChangeEvent) {
    }

}

private class Window2 : Window {

    public constructor() : super(Description.Window("Example", IsResizable = true)) {
        this.Raise()
    }

    public override fun close() {
        super.close()
    }

}
