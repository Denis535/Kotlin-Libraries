package com.denis535.game_framework_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

public class Tests_00 {

    @Test
    public fun Test_00() {
        Program().use {
        }
    }

}
// Main
internal class Program : AbstractProgram {

    private val Theme: Theme
    private val Screen: Screen
    private val Router: Router
    private val Application: Application

    public constructor() {
        this.Application = Application()
        this.Router = Router()
        this.Screen = Screen()
        this.Theme = Theme()
    }

    protected override fun OnClose() {
        this.Theme.close()
        this.Screen.close()
        this.Router.close()
        this.Application.close()
    }

}
// UI
internal class Theme : AbstractTheme {

    public constructor() {
        this.Machine.SetRoot(MainPlayList().State, null, null)
        this.Machine.SetRoot(GamePlayList().State, null, null)
    }

    protected override fun OnClose() {
        this.Machine.Root!!.close()
    }

}

internal class MainPlayList : AbstractPlayList {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

internal class GamePlayList : AbstractPlayList {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}
// UI
internal class Screen : AbstractScreen {

    public constructor() {
        this.Machine.SetRoot(RootWidget().Node, null, null)
    }

    protected override fun OnClose() {
        this.Machine.Root!!.close()
    }

}

internal class RootWidget : AbstractWidget {

    public constructor() {
        this.NodeMutable.AddChild(MainWidget().Node, null)
        this.NodeMutable.AddChild(GameWidget().Node, null)
    }

    protected override fun OnClose() {
        this.NodeMutable.Children.asReversed().CloseAll()
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

internal class MainWidget : AbstractViewableWidget {
    internal class View {
        public constructor()
    }

    public constructor() {
        this.View = View()
    }

    protected override fun OnClose() {
        this.NodeMutable.Children.asReversed().CloseAll()
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

internal class GameWidget : AbstractViewableWidget {
    internal class View {
        public constructor()
    }

    public constructor() {
        this.View = View()
    }

    protected override fun OnClose() {
        this.NodeMutable.Children.asReversed().CloseAll()
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}
// UI
internal class Router : AbstractRouter {

    public constructor()

    protected override fun OnClose() {
    }

}
// App
internal class Application : AbstractApplication {

    public val Game: Game

    public constructor() {
        this.Game = Game()
    }

    protected override fun OnClose() {
        this.Game.close()
    }

}
// Game
internal class Game : AbstractGame {

    public constructor()

    protected override fun OnClose() {
    }

}
