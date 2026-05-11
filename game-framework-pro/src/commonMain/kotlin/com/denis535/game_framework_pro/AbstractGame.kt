package com.denis535.game_framework_pro

public abstract class AbstractGame : AbstractCloseable {

    public constructor()

    protected override fun OnCloseInternal() {}

}

public abstract class AbstractPlayer : AbstractCloseable {

    public constructor()

    protected override fun OnCloseInternal() {}

}

public abstract class AbstractWorld : AbstractCloseable {

    public constructor()

    protected override fun OnCloseInternal() {}

}

public abstract class AbstractEntity : AbstractCloseable {

    public constructor()

    protected override fun OnCloseInternal() {}

}
