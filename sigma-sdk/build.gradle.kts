plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.sigma.sdk"
    compileSdk = 34
    namespace   = "com.sigma.sdk"
    compileSdk  = 34

    defaultConfig {
        minSdk = 21
android {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
    kotlin { jvmToolchain(17) }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
        singleVariant("release") { withSourcesJar() }
    }
}

afterEvaluate {
                name = "GitHub"
                url  = uri("https://maven.pkg.github.com/trysigma/sigma-device-sdk")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                    username = findProperty("gpr.user")!!.toString()
                    password = findProperty("gpr.key")!!.toString()
                }
            }
        }
