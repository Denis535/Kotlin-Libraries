plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0-RC"
    this.id("org.jetbrains.dokka") version "2.1.0"
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
    this.js(this.IR) {
        this.browser()
        this.nodejs()
    }
    this.mingwX64()
    this.linuxX64()
    this.linuxArm64()
    this.androidNativeArm32()
    this.androidNativeArm64()
    this.androidNativeX86()
    this.androidNativeX64()
    this.sourceSets {
        val commonMain by getting {
            this.dependencies {}
        }
        val jvmTest by getting {
            this.dependencies {
                this.implementation(this.kotlin("test"))
            }
        }
    }
}

signing {
    this.useGpgCmd()
    this.sign(publishing.publications)
}

publishing {
    val javadocJar = tasks.register("javadocJar", Jar::class) {
        this.group = "documentation"
        this.description = "Assembles a JAR containing the Dokka HTML documentation"
        this.archiveClassifier.set("javadoc")
        val dokkaTask = tasks.named("dokkaGeneratePublicationHtml")
        this.dependsOn(dokkaTask)
        this.from(dokkaTask.map { it.outputs.files })
    }
    this.publications.withType<MavenPublication>().configureEach {
        this.artifact(javadocJar)
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
    this.repositories {
        this.maven {
            this.name = "Local"
            this.url = uri("distribution")
        }
    }
}

tasks.withType<PublishToMavenRepository>().configureEach {
    this.dependsOn(tasks.withType<Sign>())
}

tasks.register("test") {
    this.group = "verification"
    this.description = "Run all tests"
    this.dependsOn("jvmTest")
}

tasks.named<Test>("jvmTest") {
    this.useJUnitPlatform()
}
