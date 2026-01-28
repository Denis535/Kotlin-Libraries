package com.denis535.game_engine_pro

import kotlinx.cinterop.*

public open class ServerEngine : Engine {

    @OptIn(ExperimentalForeignApi::class)
    public constructor(manifest: Manifest) : super(manifest)

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        super.close()
    }

}
