package com.denis535.game_engine_pro

import kotlinx.serialization.json.*

public object JsonUtils {

    public fun Decode(data: ByteArray): JsonElement {
        return Json.parseToJsonElement(data.decodeToString())
    }

    public fun Encode(json: JsonElement): ByteArray {
        return json.toString().encodeToByteArray()
    }

}