package com.denis535.game_engine_pro

public interface AssetLoader {

    public fun LoadAsset(path: String): ByteArray
    public fun LoadAssetAsync(path: String, callback: (LoadAssetAsyncResult) -> Unit)

}

public sealed class LoadAssetAsyncResult {
    public class Completed(public val Asset: ByteArray) : LoadAssetAsyncResult()
    public class Faulted(public val Error: String) : LoadAssetAsyncResult()
    public object Canceled : LoadAssetAsyncResult()
}

public fun AssetLoader.LoadBinaryAsset(path: String): BinaryAsset {
    val asset = this.LoadAsset(path)
    return BinaryAsset(asset)
}

public fun AssetLoader.LoadTextAsset(path: String): TextAsset {
    val asset = this.LoadAsset(path)
    return TextAsset(asset.decodeToString())
}
