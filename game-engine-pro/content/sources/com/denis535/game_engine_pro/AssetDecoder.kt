package com.denis535.game_engine_pro

import kotlinx.serialization.json.*

public object AssetDecoder {

    public fun ByteArray.ParseToJson(): JsonElement {
        return Json.parseToJsonElement(this.decodeToString())
    }

//    public fun ByteArray.DecodeToImage(): Unit {
//    }
//
//    public fun ByteArray.DecodeToVideo(): Unit {
//    }
//
//    public fun ByteArray.DecodeToMusic(): Unit {
//    }
//    public fun ByteArray.DecodeToSound(): Unit {
//    }
//
//    public fun ByteArray.DecodeToFont(): Unit {
//    }
//
//    public fun ByteArray.DecodeToGLTF(): Unit {
//    }

}
