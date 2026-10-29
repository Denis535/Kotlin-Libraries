package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.audio.*
import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.storage.*
import com.denis535.game_engine_pro.video.*

public open class ClientEngine2 : ClientEngine {

    public constructor(
        manifest: Manifest,
        window: (ClientEngine) -> Window,
        video: (ClientEngine) -> Video,
        audio: (ClientEngine) -> Audio,
    ) : super(manifest) {
        this.Window = window(this)
        this.Cursor = Cursor()
        this.Video = video(this)
        this.Audio = audio(this)
        this.Touchscreen = Touchscreen()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
        this.Gamepads = listOf(Gamepad(), Gamepad(), Gamepad(), Gamepad())
        this.Content = Content(null)
        this.Storage = Storage(this.Manifest.Group, this.Manifest.Name)
    }

    public constructor(
        manifest: Manifest,
        api: GraphicsApi = GraphicsApi.OpenGL,
        isFullScreen: Boolean = false,
        width: Int = 1280,
        height: Int = 720,
        isResizable: Boolean = false
    ) : super(manifest) {
        this.Window = Window(manifest.Title, api, isFullScreen, width, height, isResizable)
        this.Cursor = Cursor()
        this.Video = Video()
        this.Audio = Audio()
        this.Touchscreen = Touchscreen()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
        this.Gamepads = listOf(Gamepad(), Gamepad(), Gamepad(), Gamepad())
        this.Content = Content(null)
        this.Storage = Storage(this.Manifest.Group, this.Manifest.Name)
    }

    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Storage!!.close()
        this.Content!!.close()
        this.Gamepads!!.asReversed().forEach { it.close() }
        this.Keyboard!!.close()
        this.Mouse!!.close()
        this.Touchscreen!!.close()
        this.Audio.close()
        this.Video.close()
        this.Cursor.close()
        this.Window.close()
        super.close()
    }

}
