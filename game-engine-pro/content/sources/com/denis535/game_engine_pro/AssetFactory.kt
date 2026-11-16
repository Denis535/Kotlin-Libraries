package com.denis535.game_engine_pro

import kotlinx.serialization.json.*

public object AssetFactory {

    public fun Text(data: ByteArray): String {
        return data.decodeToString()
    }

    public fun Json(data: ByteArray): JsonElement {
        return Json.parseToJsonElement(data.decodeToString())
    }

//    public fun Image(data: ByteArray): Unit {
//    }
//
//    public fun Music(data: ByteArray): Unit {
//    }
//    public fun Sound(data: ByteArray): Unit {
//    }
//
//    public fun Font(data: ByteArray): Unit {
//    }
//
//    public fun GLTF(data: ByteArray): Unit {
//    }

}
