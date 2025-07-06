// settings.gradle.kts (корень репозитория sigma-device-sdk)
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.library") version "8.4.1"
        id("org.jetbrains.kotlin.android") version "1.9.24"
        id("maven-publish")          version "1.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "sigma-device-sdk"
include(":sigma-sdk")
