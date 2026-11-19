package com.denis535.game_engine_pro

import kotlinx.serialization.json.*
import kotlinx.cinterop.*
import webp.*
import opusfile.*
import freetype.*

public object AssetDecoder {

    public fun ByteArray.DecodeToJson(): JsonElement {
        return Json.parseToJsonElement(this.decodeToString())
    }

//    public fun ByteArray.DecodeToImage(): Unit { // webp
//    }
//
//    public fun ByteArray.DecodeToVideo(): Unit { // mp4 (AV1)
//    }
//
//    public fun ByteArray.DecodeToAudio(): Unit { // ogg (opus)
//    }
//
//    public fun ByteArray.DecodeToFont(): Unit { // ttf
//    }
//
//    public fun ByteArray.DecodeToGLTF(): Unit { // gltf
//    }

}
