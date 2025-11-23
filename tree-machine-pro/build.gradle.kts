plugins {
    this.id("org.jetbrains.kotlin.jvm") version "2.2.21"
    this.id("signing")
    this.id("maven-publish")
//    this.id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
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

repositories {
    this.mavenCentral()
}

dependencies {
    this.testImplementation(this.kotlin("test"))
}

//signing {
//    this.useInMemoryPgpKeys(
//        File("../0x1F21E44D-sec.asc").readText(), File("../password.txt").readText()
//    )
//    this.sign(publishing.publications["mavenJava"])
//}

publishing {
    this.publications {
        this.create<MavenPublication>("mavenJava") {
            this.from(components["java"])
            this.groupId = project.group.toString()
            this.artifactId = project.name
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
                    this.connection = "scm:git:git://github.com/Denis535/Kotlin-Libraries.git"
                    this.developerConnection = "scm:git:ssh://git@github.com:Denis535/Kotlin-Libraries.git"
                    this.url = "https://github.com/Denis535/Kotlin-Libraries"
                }
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

//nexusPublishing {
//    this.repositories {
//        this.sonatype {
//            this.username.set(System.getenv("SONATYPE_USERNAME"))
//            this.password.set(System.getenv("SONATYPE_PASSWORD"))
//        }
//    }
//}

tasks.test {
    this.useJUnitPlatform()
}
