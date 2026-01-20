package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.windows.*

public fun Main(args: Array<String>) {
    ClientEngine2().use {
        it.Run()
    }
}

private class ClientEngine2 : ClientEngine {

    public constructor() : super(Manifest("Example"), { MainWindow2() }) {
    }

    public override fun close() {
        super.close()
    }

    protected override fun OnStart() {
    }

    protected override fun OnStop() {
    }

    protected override fun OnDraw() {
        this.Sleep(1U)
    }

    protected override fun OnUpdate() {
    }

    protected override fun OnFixedUpdate() {
    }

    protected override fun OnMouseFocus(event: MouseFocusEvent) {
    }

    protected override fun OnMouseFocusLost(event: MouseFocusLostEvent) {
    }

    protected override fun OnKeyboardFocus(event: KeyboardFocusEvent) {
    }

    protected override fun OnKeyboardFocusLost(event: KeyboardFocusLostEvent) {
    }

    protected override fun OnMouseMove(event: MouseMoveEvent) {
    }

    protected override fun OnMouseButtonPress(event: MouseButtonEvent) {
    }

    protected override fun OnMouseButtonRelease(event: MouseButtonEvent) {
    }

    protected override fun OnMouseWheelScroll(event: MouseWheelScrollEvent) {
    }

    protected override fun OnKeyboardKeyPress(event: KeyboardKeyEvent) {
        if (event.Key == KeyboardKey.Enter && this.Keyboard.IsKeyPressed(KeyboardKey.RightAlt)) {
            this.Window.IsFullScreen = !this.Window.IsFullScreen
        }
    }

    protected override fun OnKeyboardKeyRepeat(event: KeyboardKeyEvent) {
    }

    protected override fun OnKeyboardKeyRelease(event: KeyboardKeyEvent) {
    }

    protected override fun OnTextInput(event: TextInputEvent) {
    }

}

private class MainWindow2 : MainWindow {

    public constructor() : super(Description.Window("Example", IsResizable = true)) {
        this.Raise()
    }

    public override fun close() {
        super.close()
    }

}
