@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.*

plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0"
    this.id("signing")
    this.id("maven-publish")
//    this.id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = project.group
version = project.version
description = project.description

kotlin {
    this.mingwX64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("sources/com.denis535.sdl-x86_64-w64-mingw32.def")
            }
        }
    }
    this.linuxX64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("sources/com.denis535.sdl-x86_64-linux-gnu.def")
            }
        }
    }
    this.linuxArm64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("sources/com.denis535.sdl-aarch64-linux-gnu.def")
            }
        }
    }
    this.androidNativeX64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("sources/com.denis535.sdl-x86_64-linux-android.def")
            }
        }
    }
    this.androidNativeArm64 {
        this.compilations["main"].cinterops {
            val sdl by creating {
                this.definitionFile = file("sources/com.denis535.sdl-aarch64-linux-android.def")
            }
        }
    }
    this.sourceSets {
        val commonMain by this.getting {
            this.kotlin.srcDir("sources")
            this.resources.srcDir("resources")
        }
        val mingwX64Main by getting {}
        val linuxX64Main by getting {}
        val linuxArm64Main by getting {}
        val androidNativeX64Main by getting {}
        val androidNativeArm64Main by getting {}
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
//        this.maven {
//            this.name = "ossrh-staging-api"
//            this.url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
//            this.credentials {
//                this.username = System.getenv("SONATYPE_USERNAME")
//                this.password = System.getenv("SONATYPE_PASSWORD")
//            }
//        }
    }
}

//nexusPublishing {
//    this.repositories {
//        this.sonatype {
//            this.nexusUrl = uri("https://ossrh-staging-api.central.sonatype.com/service/local/")
//            this.snapshotRepositoryUrl = uri("https://ossrh-stage.sonatype.org/content/repositories/snapshots/")
//            this.username = System.getenv("SONATYPE_USERNAME")
//            this.password = System.getenv("SONATYPE_PASSWORD")
//        }
//    }
//}

//tasks.named("publishToMavenLocal") {
//    val url = File(System.getProperty("user.home"), ".m2/repository").toString()
//    this.doFirst {
//        println("Publishing to Maven Local: $url")
//    }
//    this.doLast {
//        publishing.publications.forEach {
//            println("Publication: ${it.name}")
//        }
//        println("All publications have been successfully published to Maven Local")
//    }
//}
