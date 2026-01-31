plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0"
}

kotlin {
    this.mingwX64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("x86_64-w64-mingw32/com.denis535.sdl.def")
            }
        }
    }
    this.linuxX64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("x86_64-linux-gnu/com.denis535.sdl.def")
            }
        }
    }
    this.sourceSets {
        val commonMain by this.getting {
            this.kotlin.srcDir("sources")
            this.resources.srcDir("resources")
            this.dependencies {}
        }
        val mingwX64Main by getting {}
        val linuxX64Main by getting {}
    }
}
