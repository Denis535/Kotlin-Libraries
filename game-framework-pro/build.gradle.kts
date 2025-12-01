plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0-RC"
    this.id("signing")
    this.id("maven-publish")
//    this.id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = project.group
version = project.version
description = project.description

kotlin {
    this.jvm {}
    this.jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(21))
    }
    this.sourceSets {
        val commonMain by getting {
            this.dependencies {
                this.implementation("io.github.denis535:state-machine-pro:1.0.0")
                this.implementation("io.github.denis535:tree-machine-pro:1.0.0")
            }
        }
        val jvmTest by getting {
            this.dependencies {
                this.implementation(this.kotlin("test"))
            }
        }
    }
}

publishing {
    this.repositories {
        this.maven {
            this.name = "Local"
            this.url = uri("distribution")
        }
    }
    this.publications.withType<MavenPublication>().configureEach {
        this.pom {
            this.name.set(project.name)
            this.description.set(project.description)
            this.url.set("https://github.com/Denis535/Kotlin-Libraries")
            this.licenses {
                this.license {
                    this.name.set("MIT License")
                    this.url.set("https://opensource.org/licenses/MIT")
                }
            }
            this.developers {
                this.developer {
                    this.id.set("denis535")
                    this.name.set("Denis535")
                }
            }
            this.scm {
                this.connection = "scm:git:git://github.com/Denis535/Kotlin-Libraries.git"
                this.developerConnection = "scm:git:ssh://git@github.com:Denis535/Kotlin-Libraries.git"
                this.url = "https://github.com/Denis535/Kotlin-Libraries"
            }
        }
    }
}

signing {
    this.useGpgCmd()
    this.sign(publishing.publications)
}

tasks.named<Test>("jvmTest") {
    this.useJUnitPlatform()
}

tasks.register("test") {
    this.group = "verification"
    this.description = "Run all tests"
    this.dependsOn("jvmTest")
}
