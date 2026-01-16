package com.denis535.example

import com.denis535.game_engine_pro.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.windows.*
import kotlinx.cinterop.*
import kotlin.reflect.*

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

    protected override fun OnStart(info: FrameInfo) {
    }

    protected override fun OnStop(info: FrameInfo) {
    }

    protected override fun OnMouseMove(event: MouseMoveEvent) {
        super.OnMouseMove(event)
    }

    protected override fun OnMouseButtonPress(event: MouseButtonActionEvent) {
        super.OnMouseButtonPress(event)
    }

    protected override fun OnMouseButtonRelease(event: MouseButtonActionEvent) {
        super.OnMouseButtonRelease(event)
    }

    protected override fun OnMouseWheelScroll(event: MouseWheelScrollEvent) {
        super.OnMouseWheelScroll(event)
    }

    protected override fun OnKeyboardKeyPress(event: KeyboardKeyActionEvent) {
//        if (event.Key == KeyboardKey.Enter && this.Keyboard.IsKeyPressed(KeyboardKey.LeftAlt)) {
//            this.Window.IsFullScreen = !this.Window.IsFullScreen
//        }
        super.OnKeyboardKeyPress(event)
    }

    protected override fun OnKeyboardKeyRepeat(event: KeyboardKeyActionEvent) {
        super.OnKeyboardKeyRepeat(event)
    }

    protected override fun OnKeyboardKeyRelease(event: KeyboardKeyActionEvent) {
        super.OnKeyboardKeyRelease(event)
    }

    protected override fun OnTextInput(text: String) {
        super.OnTextInput(text)
    }

    protected override fun OnFixedUpdate(info: FrameInfo) {
    }

    protected override fun OnUpdate(info: FrameInfo) {
    }

    protected override fun OnDraw(info: FrameInfo) {
    }

}

private class MainWindow2 : MainWindow {

    public constructor() : super(Description.Window("Example")) {
        this.Raise()
    }

    public override fun close() {
        super.close()
    }

}
