pluginManagement {
    repositories { google(); mavenCentral() }
    plugins {
        id("com.android.library")           version "8.4.1"
        id("org.jetbrains.kotlin.android")  version "1.9.24"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories { google(); mavenCentral() }
}

rootProject.name = "sigma-device-sdk"
include(":sigma-sdk")
