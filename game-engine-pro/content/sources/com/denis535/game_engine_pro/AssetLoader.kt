package com.denis535.game_engine_pro

import cnames.structs.*
import kotlinx.cinterop.*

public interface AssetLoader {
    public fun Load(path: String): ByteArray
    public fun LoadAsync(path: String, callback: (LoadAsyncResult) -> Unit)
}

public sealed class LoadAsyncResult {
    public class Completed(public val Data: ByteArray) : LoadAsyncResult()
    public class Faulted(public val Error: String) : LoadAsyncResult()
    public class Canceled : LoadAsyncResult()
}

public fun AssetLoader.LoadBinary(path: String): BinaryAsset {
    val data = this.Load(path)
    return BinaryAsset(data)
}

public fun AssetLoader.LoadText(path: String): TextAsset {
    val text = this.Load(path).decodeToString()
    return TextAsset(text)
}
