package com.denis535.game_engine_pro

public open class ServerEngine : Engine {

    public constructor(manifest: Manifest) : super(manifest)

    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        super.close()
    }

}
