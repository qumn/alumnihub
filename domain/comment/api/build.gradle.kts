import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

project.archivesName.set("comment-api")

dependencies {
    implementation(project(":framework:exception"))
    implementation(project(":domain:system:api"))
    implementation(project(":common:util"))
}