rootProject.name = "fx-commission"
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
include(":customer")
include(":shared")
include(":account")
include("transaction")
