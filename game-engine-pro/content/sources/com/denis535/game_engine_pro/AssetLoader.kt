package com.denis535.game_engine_pro

import cnames.structs.*
import kotlinx.cinterop.*

public interface AssetLoader {

    public fun <T : Asset> LoadInternal(
        path: String,
        transformer: (ByteArray) -> T
    ): T

    public fun <T : Asset> LoadAsyncInternal(
        path: String,
        callback: (AssetAsyncResult<T>) -> Unit,
        transformer: (ByteArray) -> T
    )

}

public sealed class AssetAsyncResult<out T : Asset> {

    public class Completed<out T : Asset>(
        public val Asset: T
    ) : AssetAsyncResult<T>()

    public class Faulted(
        public val Error: String
    ) : AssetAsyncResult<Nothing>()

    public object Canceled : AssetAsyncResult<Nothing>()
}

public fun AssetLoader.LoadBinary(path: String): BinaryAsset {
    return this.LoadInternal(path) { data ->
        BinaryAsset(data)
    }
}

public fun AssetLoader.LoadText(path: String): TextAsset {
    return this.LoadInternal(path) { data ->
        TextAsset(data.decodeToString())
    }
}

