package com.denis535.game_engine_pro

public interface AssetLoader {
    public fun <T : Asset> LoadAssetInternal(path: String, transformer: (ByteArray) -> T): T
    public fun <T : Asset> LoadAssetAsyncInternal(path: String, callback: (LoadAssetAsyncResult<T>) -> Unit, transformer: (ByteArray) -> T)
}

public sealed class LoadAssetAsyncResult<out T : Asset> {
    public class Completed<out T : Asset>(public val Asset: T) : LoadAssetAsyncResult<T>()
    public class Faulted(public val Error: String) : LoadAssetAsyncResult<Nothing>()
    public object Canceled : LoadAssetAsyncResult<Nothing>()
}

public fun AssetLoader.LoadBinary(path: String): BinaryAsset {
    return this.LoadAssetInternal(path) { data ->
        BinaryAsset(data)
    }
}

public fun AssetLoader.LoadText(path: String): TextAsset {
    return this.LoadAssetInternal(path) { data ->
        TextAsset(data.decodeToString())
    }
}
