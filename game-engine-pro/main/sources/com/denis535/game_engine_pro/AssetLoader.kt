package com.denis535.game_engine_pro

public fun Content.LoadText(path: String): TextAsset {
    val data = this.Load(path)
    return TextAsset(data.decodeToString())
}
