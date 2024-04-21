import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

project.archivesName.set("system-api")

dependencies {
    implementation(project(":framework:test"))
    implementation(project(":framework:exception"))
    implementation(project(":common:util"))
    implementation(libs.jackson.annotations)
}