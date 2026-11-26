package com.denis535.game_engine_pro.io

import kotlinx.serialization.json.*

public object JsonDecoder {

    public fun Decode(data: ByteArray): JsonElement {
        return Json.parseToJsonElement(data.decodeToString())
    }

}

public object JsonEncoder {

    public fun Encode(json: JsonElement): ByteArray {
        return json.toString().encodeToByteArray()
    }

}
