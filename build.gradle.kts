buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}
plugins {
    id("com.android.application")
            .version("8.0.2")
            .apply(false)

    id("com.android.library")
            .version("8.0.2")
            .apply(false)

    id("org.jetbrains.kotlin.android")
            .version("1.8.20")
            .apply(false)
}
