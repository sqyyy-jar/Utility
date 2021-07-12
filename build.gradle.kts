plugins {
    kotlin("jvm") version "1.5.20"
    id("org.jetbrains.dokka") version "1.5.0"
}

group = "dev.sqyyy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.9.17")
}

apply(plugin="org.jetbrains.dokka")

tasks.dokkaHtml {
    dokkaSourceSets {
        configureEach {
            includes.from("properties.md")
        }
    }
}