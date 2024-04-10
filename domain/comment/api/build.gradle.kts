plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":framework:exception"))
    implementation(project(":domain:system:api"))
    implementation(project(":common:util"))
}