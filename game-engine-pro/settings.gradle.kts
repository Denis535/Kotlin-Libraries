pluginManagement {
    this.repositories {
        this.gradlePluginPortal()
        this.mavenCentral()
    }
}

dependencyResolutionManagement {
    this.repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    this.repositories {
        this.mavenCentral()
    }
}

plugins {
    this.id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "game-engine-pro"

include(":main")
include(":sdl")
