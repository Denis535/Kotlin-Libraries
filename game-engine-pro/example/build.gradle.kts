plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0"
}

group = project.group
version = project.version
description = project.description

kotlin {
    this.mingwX64 {
        this.binaries {
            this.executable {
                this.baseName = "KotlinGameExample"
                this.entryPoint = "com.denis535.example.Main"
                this.linkerOpts(
                    "-Wl,-subsystem,windows",
                )
            }
        }
    }
    this.linuxX64 {
        this.binaries {
            this.executable {
                this.baseName = "KotlinGameExample"
                this.entryPoint = "com.denis535.example.Main"
                this.linkerOpts()
            }
        }
    }
//    this.linuxArm64 {
//        this.binaries {
//            this.executable {
//                this.baseName = "KotlinGameExample"
//                this.entryPoint = "com.denis535.example.Main"
//                this.linkerOpts()
//            }
//        }
//    }
    this.sourceSets {
        val commonMain by this.getting {
            this.kotlin.srcDir("sources")
            this.resources.srcDir("resources")
            this.dependencies {
                this.implementation(this.project(":game-engine-pro"))
            }
        }
        val mingwX64Main by getting {}
        val linuxX64Main by getting {}
//        val linuxArm64Main by getting {}
    }
}
