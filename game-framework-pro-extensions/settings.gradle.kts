plugins {
    this.id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    this.repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    this.repositories {
        this.mavenCentral()
        this.maven {
            this.url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "game-framework-pro-extensions"
