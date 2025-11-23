plugins {
    this.id("org.jetbrains.kotlin.jvm") version "2.2.21"
    this.id("maven-publish")
}

group = project.group
version = project.version
description = project.description

java {
    this.withSourcesJar()
    this.withJavadocJar()
}

kotlin {
    this.jvmToolchain(21)
    this.compilerOptions {
        this.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}

dependencies {
    this.api("com.github.Denis535.Kotlin-Libraries:state-machine-pro:v1.0.0")
    this.api("com.github.Denis535.Kotlin-Libraries:tree-machine-pro:v1.0.0")
}

publishing {
    this.publications {
        this.create<MavenPublication>("mavenJava") {
            this.from(components["java"])
            this.groupId = project.group.toString()
            this.artifactId = rootProject.name
            this.version = project.version.toString()
            this.pom {
                this.name = project.name
                this.description = project.description
                this.url = project.findProperty("url").toString()
                this.licenses {
                    this.license {
                        this.name = "MIT License"
                        this.url = "https://opensource.org/licenses/MIT"
                    }
                }
                this.developers {
                    this.developer {
                        this.id = "denis535"
                        this.name = "Denis535"
                    }
                }
                this.scm {
                    this.connection = "scm:git:git://github.com/Denis535/Kotlin.Libraries.git"
                    this.developerConnection = "scm:git:ssh://git@github.com:Denis535/Kotlin.Libraries.git"
                    this.url = "https://github.com/Denis535/Kotlin.Libraries"
                }
            }
        }
    }
}

tasks.test {
    this.useJUnitPlatform()
}
