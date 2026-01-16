plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0"
    this.id("signing")
    this.id("maven-publish")
}

group = project.group
version = project.version
description = project.description

kotlin {
    this.mingwX64()
    this.linuxX64()
    this.linuxArm64()
    this.sourceSets {
        val commonMain by this.getting {
            this.kotlin.srcDir("sources")
            this.resources.srcDir("resources")
            this.dependencies {
                this.implementation("io.github.denis535:sdl:3.4.0.4")
            }
        }
        val mingwX64Main by getting {}
        val linuxX64Main by getting {}
        val linuxArm64Main by getting {}
    }
}

signing {
    this.useGpgCmd()
    this.sign(publishing.publications)
}

publishing {
    this.publications.withType<MavenPublication>().configureEach {
        this.pom {
            this.name = rootProject.name
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
