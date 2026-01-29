plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0"
}

kotlin {
    this.mingwX64 {
        this.binaries {
            this.executable {
                this.baseName = "Example"
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
                this.baseName = "Example"
                this.entryPoint = "com.denis535.example.Main"
                this.linkerOpts(
                    "-Llibs/SDL/x86_64-linux-gnu/lib",
                    "-lSDL3",
//                    "-Wl,--verbose",
                    "-Wl,--allow-shlib-undefined",
                    "-Wl,-rpath,\$ORIGIN",
                )
            }
        }
    }
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
    }
}

val OperationSystem = System.getProperty("os.name")!!
if (OperationSystem.lowercase().contains("windows")) {
    tasks.register<Exec>("run") {
        val executable = kotlin.mingwX64().binaries.getExecutable("DEBUG")
        this.dependsOn(executable.linkTaskProvider)
        this.environment(
            "PATH", listOfNotNull(
                "../libs/SDL/x86_64-w64-mingw32/lib", System.getenv("PATH")
            ).joinToString(";")
        )
        this.commandLine(executable.outputFile)
    }
} else if (OperationSystem.lowercase().contains("linux")) {
    tasks.register<Exec>("run") {
        val executable = kotlin.linuxX64().binaries.getExecutable("DEBUG")
        this.dependsOn(executable.linkTaskProvider)
        this.environment(
            "LD_LIBRARY_PATH", listOfNotNull(
                "../libs/SDL/x86_64-linux-gnu/lib", System.getenv("LD_LIBRARY_PATH")
            ).joinToString(":")
        )
        this.commandLine(executable.outputFile)
    }
}
