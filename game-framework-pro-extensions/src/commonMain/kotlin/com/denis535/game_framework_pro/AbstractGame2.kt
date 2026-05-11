package com.denis535.game_framework_pro

public abstract class AbstractGame2 : AbstractGame {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return DependencyProvider.Instance!!
        }

    public constructor()

    protected final override fun OnCloseInternal() {
        super.OnCloseInternal()
    }

}

public abstract class AbstractPlayer2 : AbstractPlayer {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return DependencyProvider.Instance!!
        }

    public constructor()

    protected final override fun OnCloseInternal() {
        super.OnCloseInternal()
    }

}

public abstract class AbstractWorld2 : AbstractWorld {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return DependencyProvider.Instance!!
        }

    public constructor()

    protected final override fun OnCloseInternal() {
        super.OnCloseInternal()
    }

}

public abstract class AbstractEntity2 : AbstractEntity {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return DependencyProvider.Instance!!
        }

    public constructor()

    protected final override fun OnCloseInternal() {
        super.OnCloseInternal()
    }

}
