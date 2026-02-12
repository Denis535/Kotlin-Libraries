package com.denis535.game_engine_pro

import cnames.structs.*
import kotlinx.cinterop.*

public interface AssetLoader {
    public fun<T> LoadInternal(path: String): AssetResult<T> where T : Asset
    public fun<T> LoadAsyncInternal(path: String, callback: (AssetResult<T>) -> Unit) where T : Asset
}

public sealed class AssetResult<T> where T : Asset {
    public class Completed(public val Asset: T) : AssetResult() 
    public class Faulted(public val Error: String) : AssetResult()
    public class Canceled : AssetResult()
}

public fun AssetLoader.LoadBinary(path: String): BinaryAsset {
    val data = this.LoadInternal(path)
    return BinaryAsset(data)
}

public fun AssetLoader.LoadText(path: String): TextAsset {
    val text = this.LoadInternal(path).decodeToString()
    return TextAsset(text)
}
