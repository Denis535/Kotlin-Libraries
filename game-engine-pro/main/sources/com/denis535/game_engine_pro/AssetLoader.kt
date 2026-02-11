package com.denis535.game_engine_pro

public fun Content.LoadBinary(path: String): BinaryAsset {
    val data = this.Load(path)
    return BinaryAsset(data)
}

public fun Content.LoadText(path: String): TextAsset {
    val text = this.Load(path).decodeToString()
    return TextAsset(text)
}
