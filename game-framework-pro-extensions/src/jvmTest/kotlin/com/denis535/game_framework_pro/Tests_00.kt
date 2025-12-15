package com.denis535.game_framework_pro

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

public class Tests_00 {

    @Test
    public fun Test_00() {
        Program().use {
            it.RequireDependency<Program>(AbstractProgram::class)
            it.RequireDependency<Program>(AbstractProgram2::class)
            it.RequireDependency<Program>(Program::class)

            it.RequireDependency<Theme>(AbstractTheme::class)
            it.RequireDependency<Theme>(AbstractTheme2::class)
            it.RequireDependency<Theme>(Theme::class)

            it.RequireDependency<Screen>(AbstractScreen::class)
            it.RequireDependency<Screen>(AbstractScreen2::class)
            it.RequireDependency<Screen>(Screen::class)

            it.RequireDependency<Router>(AbstractRouter::class)
            it.RequireDependency<Router>(AbstractRouter2::class)
            it.RequireDependency<Router>(Router::class)

            it.RequireDependency<Application>(AbstractApplication::class)
            it.RequireDependency<Application>(AbstractApplication2::class)
            it.RequireDependency<Application>(Application::class)
        }
    }

}
// Main
internal class Program : AbstractProgram2<Theme, Screen, Router, Application> {

    public constructor() {
        this.Application = Application()
        this.Router = Router()
        this.Screen = Screen()
        this.Theme = Theme()
    }

    protected override fun OnClose() {
        this.Theme!!.close()
        this.Screen!!.close()
        this.Router!!.close()
        this.Application!!.close()
        super.OnClose()
    }

}
// UI
internal class Theme : AbstractTheme2<Router, Application> {

    public constructor() {
        this.Machine.SetRoot(MainPlayList().State, null, null)
        this.Machine.SetRoot(GamePlayList().State, null, null)
    }

    protected override fun OnClose() {
        this.Machine.Root!!.close()
    }

}

internal class MainPlayList : AbstractPlayList2 {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}

internal class GamePlayList : AbstractPlayList2 {

    public constructor()

    protected override fun OnClose() {
    }

    protected override fun OnActivate(argument: Any?) {
    }

    protected override fun OnDeactivate(argument: Any?) {
    }

}
// UI
internal class Screen : AbstractScreen2<Router, Application> {

    public constructor() {
        this.Machine.SetRoot(RootWidget().Node, null, null)
    }

    protected override fun OnClose() {
        this.Machine.Root!!.close()
    }

}

internal class RootWidget : AbstractWidget2 {

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

internal class MainWidget : AbstractViewableWidget2 {
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

internal class GameWidget : AbstractViewableWidget2 {
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
internal class Router : AbstractRouter2<Theme, Screen, Application> {

    public constructor()

    protected override fun OnClose() {
    }

}
// App
internal class Application : AbstractApplication2 {

    public val Game: Game

    public constructor() {
        this.Game = Game()
    }

    protected override fun OnClose() {
        this.Game.close()
    }

}
// Game
internal class Game : AbstractGame2 {

    public constructor()

    protected override fun OnClose() {
    }

}
