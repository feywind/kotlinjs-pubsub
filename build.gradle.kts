plugins {
    kotlin("js") version "1.4.21"
}

group = "me.mzp"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")

    // Normally we'd want Dukat to generate typings for Kotlin, but it fails on this package.
    implementation(npm("@google-cloud/pubsub", "^2.12.0" /*, generateExternals = true*/))
}

kotlin {
    js(LEGACY) {
        nodejs {
            binaries.executable()
        }
    }
}
