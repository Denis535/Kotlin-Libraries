plugins {
    this.id("org.jetbrains.kotlin.jvm") version "2.2.21"
    this.id("maven-publish")
}

publishing {
    this.publications {
        this.create<MavenPublication>("mavenJava") {
            this.from(components["java"])
        }
    }
}
