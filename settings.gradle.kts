pluginManagement {
    repositories {
        gradlePluginPortal() // <-- Add this line
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "rotasEscolares"
include(":app")
