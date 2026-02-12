package com.denis535.game_engine_pro

public interface AssetLoader {
    public fun <T : Asset> LoadAsset(path: String, factory: (ByteArray) -> T): T
    public fun <T : Asset> LoadAssetAsync(path: String, callback: (LoadAssetAsyncResult<T>) -> Unit, factory: (ByteArray) -> T)
}

public sealed class LoadAssetAsyncResult<out T : Asset> {
    public class Completed<out T : Asset>(public val Asset: T) : LoadAssetAsyncResult<T>()
    public class Faulted(public val Error: String) : LoadAssetAsyncResult<Nothing>()
    public object Canceled : LoadAssetAsyncResult<Nothing>()
}

public fun AssetLoader.LoadBinaryAsset(path: String): BinaryAsset {
    return this.LoadAsset(path) { data ->
        BinaryAsset(data)
    }
}

public fun AssetLoader.LoadTextAsset(path: String): TextAsset {
    return this.LoadAsset(path) { data ->
        TextAsset(data.decodeToString())
    }
}
