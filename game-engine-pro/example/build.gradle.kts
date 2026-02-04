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
//                    "-Wl,--verbose",
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
//                    "-Wl,--verbose",
                    "-Wl,-rpath,\$ORIGIN",
                    "-Wl,--allow-shlib-undefined",
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
        this.commandLine(executable.outputFile)
    }
} else if (OperationSystem.lowercase().contains("linux")) {
    tasks.register<Exec>("run") {
        val executable = kotlin.linuxX64().binaries.getExecutable("DEBUG")
        this.dependsOn(executable.linkTaskProvider)
        this.commandLine(executable.outputFile)
    }
}
