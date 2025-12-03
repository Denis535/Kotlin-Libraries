@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

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
    this.wasmJs {
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
    val javadocJar = tasks.register<Jar>("javadocJar") {
        this.group = "documentation"
        this.description = "Assembles a JAR containing the Dokka HTML documentation"
        this.archiveClassifier.set("javadoc")
        val dokkaTask = tasks.named("dokkaGeneratePublicationHtml")
        this.dependsOn(dokkaTask)
        this.from(dokkaTask.map { it.outputs.files })
    }
    this.repositories {
        this.maven {
            this.name = "Local"
            this.url = uri("distribution")
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
    this.publications.withType<MavenPublication>().configureEach {
        if (this.name == "jvm") {
            this.artifact(javadocJar)
        }
        this.pom {
            this.name.set(project.name)
            this.description.set(project.description)
            this.url.set(project.property("url").toString())
            this.licenses {
                this.license {
                    this.name.set("MIT License")
                    this.url.set("https://opensource.org/licenses/MIT")
                }
            }
            this.developers {
                this.developer {
                    this.id.set(project.property("developer.id").toString())
                    this.name.set(project.property("developer.name").toString())
                }
            }
            this.scm {
                this.connection = project.property("scm.connection").toString()
                this.developerConnection = project.property("scm.developerConnection").toString()
                this.url = project.property("scm.url").toString()
            }
        }
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

tasks.named("publishToMavenLocal") {
    val url = File(System.getProperty("user.home"), ".m2/repository").toString()
    this.doFirst {
        println("Publishing to Maven Local: $url")
    }
    this.doLast {
        publishing.publications.forEach {
            println("Publication: ${it.name}")
        }
        println("All publications have been successfully published to Maven Local")
    }
}

tasks.register("test") {
    this.group = "verification"
    this.description = "Run all tests"
    this.dependsOn("jvmTest")
}

tasks.named<Test>("jvmTest") {
    this.useJUnitPlatform()
}
