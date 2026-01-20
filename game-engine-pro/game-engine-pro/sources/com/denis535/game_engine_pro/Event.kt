package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.input.*

public class MouseFocusEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class MouseFocusLostEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class KeyboardFocusEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class KeyboardFocusLostEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class TextInputEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Text: String,
)

public class MouseMoveEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Cursor: Pair<Float, Float>, // unlocked cursor only
    public val Delta: Pair<Float, Float>, // locked cursor only
)

public class MouseButtonEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Cursor: Pair<Float, Float>, // unlocked cursor only
    public val Button: MouseButton,
    public val ClickCount: Int,
)

public class MouseWheelScrollEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Cursor: Pair<Float, Float>, // unlocked cursor only
    public val Scroll: Pair<Float, Float>,
    public val IntegerScroll: Pair<Int, Int>,
)

public class KeyboardKeyEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Key: KeyboardKey,
)
