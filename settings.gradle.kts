pluginManagement {
    repositories {
        google()          // ← именно здесь ищется AGP
        mavenCentral()
    }
    repositories { google(); mavenCentral() }
    plugins {
        id("com.android.library") version "8.4.1"        // AGP ≥ 8.3 для Gradle 8.7
        id("org.jetbrains.kotlin.android") version "1.9.24"
        id("com.android.library")           version "8.4.1"
        id("org.jetbrains.kotlin.android")  version "1.9.24"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    repositories { google(); mavenCentral() }
}

rootProject.name = "sigma-device-sdk"
