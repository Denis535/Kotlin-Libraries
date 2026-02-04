plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0"
    this.id("signing")
    this.id("maven-publish")
}

kotlin {
    this.mingwX64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("x86_64-w64-mingw32/com.denis535.internal.sdl.def")
            }
        }
    }
    this.linuxX64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("x86_64-linux-gnu/com.denis535.internal.sdl.def")
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

signing {
    this.useGpgCmd()
    this.sign(publishing.publications)
}

publishing {
    this.publications.withType<MavenPublication>().configureEach {
        this.pom {
            this.name = project.name
            this.description = project.description
            this.url = project.property("url").toString()
            this.licenses {
                this.license {
                    this.name = "MIT License"
                    this.url = "https://opensource.org/licenses/MIT"
                }
            }
            this.developers {
                this.developer {
                    this.id = project.property("developer.id").toString()
                    this.name = project.property("developer.name").toString()
                }
            }
            this.scm {
                this.url = project.property("scm.url").toString()
                this.connection = project.property("scm.connection").toString()
                this.developerConnection = project.property("scm.developerConnection").toString()
            }
        }
    }
    this.repositories {
        this.maven {
            this.name = "Local"
            this.url = uri("dist")
        }
    }
}
