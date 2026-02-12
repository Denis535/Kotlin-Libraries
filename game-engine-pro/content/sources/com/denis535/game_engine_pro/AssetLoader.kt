package com.denis535.game_engine_pro

import cnames.structs.*
import kotlinx.cinterop.*

public interface AssetLoader {
    public fun<T> LoadInternal(path: String, transformer: (ByteArray) -> T): T where T : Asset
    public fun<T> LoadAsyncInternal(path: String, transformer: (ByteArray) -> T, callback: (AssetResult<T>) -> Unit) where T : Asset
}

public sealed class AssetAsyncResult<T> where T : Asset {
    public class Completed(public val Asset: T) : AssetAsyncResult() 
    public class Faulted(public val Error: String) : AssetAsyncResult()
    public class Canceled : AssetAsyncResult()
}

public fun AssetLoader.LoadBinary(path: String): BinaryAsset {
    val data = this.LoadInternal(path)
    return BinaryAsset(data)
}

public fun AssetLoader.LoadText(path: String): TextAsset {
    val text = this.LoadInternal(path).decodeToString()
    return TextAsset(text)
}
