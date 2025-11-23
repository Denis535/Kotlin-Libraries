plugins {
    this.id("org.jetbrains.kotlin.jvm") version "2.2.21"
    this.id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
