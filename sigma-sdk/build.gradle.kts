plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace   = "com.sigma.sdk"
    compileSdk  = 34

    defaultConfig {
        minSdk = 21
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin { jvmToolchain(17) }

    publishing {
        singleVariant("release") { withSourcesJar() }
    }
}

afterEvaluate {
    publishing {
        publications.create<MavenPublication>("release") {
            from(components["release"])
            groupId    = "com.sigma"
            artifactId = "sdk"
            version    = "1.0.0"
        }
        repositories {
            maven {
                name = "GitHub"
                url  = uri("https://maven.pkg.github.com/trysigma/sigma-device-sdk")
                credentials {
                    username = System.getenv("GPR_USER") ?: error("Missing GPR_USER")
                    password = System.getenv("GPR_KEY") ?: error("Missing GPR_KEY")
                }

            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
}
