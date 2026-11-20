package com.denis535.game_engine_pro

import kotlinx.serialization.json.*
import kotlinx.cinterop.*
import webp.*
import opusfile.*
import freetype.*

public object AssetDecoder {

    public fun Json(data: ByteArray, path: String?): JsonElement {
        return Json.parseToJsonElement(data.decodeToString())
    }

//    public fun Image(data: ByteArray, path: String?): Image { // webp
//    }
//
//    public fun Video(data: ByteArray, path: String?): Pair<Video, Audio> { // mp4 (AV1)
//    }
//
//    public fun Audio(data: ByteArray, path: String?): Audio { // ogg (opus)
//    }
//
//    public fun Font(data: ByteArray, path: String?): Font { // ttf
//    }
//
//    public fun GLTF(data: ByteArray, path: String?): GLTF { // gltf
//    }

}
