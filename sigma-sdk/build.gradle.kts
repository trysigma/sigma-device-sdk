plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace = "com.sigma.sdk"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        targetSdk = 34          // warning-депрекейт — сейчас не критично
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // ⬇️ говорим AGP, что публикуем только variant release + sources
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications.create<MavenPublication>("release") {
            from(components["release"])      // теперь component уже существует
            groupId    = "com.sigma"
            artifactId = "sdk"
            version    = "1.0.0"
        }
        repositories {
            maven {
                name = "GitHub"
                url  = uri("https://maven.pkg.github.com/trysigma/sigma-device-sdk")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
