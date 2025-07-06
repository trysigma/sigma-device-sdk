
// build.gradle.kts (корневой, для публикации)
plugins {
    `maven-publish`
}

group = "com.trysigma"
version = "1.0.0"

publishing {
    publications {
        create<MavenPublication>("release") {
            // Используем компонент 'androidRelease' для Android Library
            from(components["androidRelease"])
            artifactId = "sigma-sdk"
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/trysigma/sigma-device-sdk")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "Kazymbetov"
                password = System.getenv("GITHUB_TOKEN") ?: "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"
            }
        }
    }
}
