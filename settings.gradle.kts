pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // add the following
        maven { url = uri("https://www.jitpack.io" ) }
    }
}

rootProject.name = "Book My Doctor"
include(":app")
