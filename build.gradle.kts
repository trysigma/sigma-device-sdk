plugins {
    id("com.android.library")      apply false
    id("org.jetbrains.kotlin.android") apply false
}
// sigma-device-sdk/build.gradle.kts (корень SDK-репо)
plugins {
  id("com.android.library")      version "7.2.2" apply false  // ваша версия AGP
  kotlin("android")              version "1.8.21" apply false
  id("maven-publish")            // <— добавляем
}

group = "com.trysigma"           // будет groupId=…
version = "1.0.0"                // будет version=…

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}

// Настройка публикации AAR
publishing {
  publications {
    create<MavenPublication>("release") {
      from(components["release"])
      artifactId = "sigma-sdk"    // идентификатор вашего AAR
    }
  }
  repositories {
    maven {
      name = "GitHubPackages"
      url  = uri("https://maven.pkg.github.com/trysigma/sigma-device-sdk")
      credentials {
        username = System.getenv("GITHUB_ACTOR") ?: "kazymbetov"
        password = System.getenv("GITHUB_TOKEN") ?: "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"
      }
    }
  }
}
