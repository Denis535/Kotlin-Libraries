package com.denis535.game_engine_pro

import kotlinx.cinterop.*

public open class ServerEngine : Engine {

    @OptIn(ExperimentalForeignApi::class)
    public constructor(description: Description) : super(description)

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        super.close()
    }

}
