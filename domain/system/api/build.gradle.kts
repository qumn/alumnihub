plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":framework:test"))
    implementation(project(":framework:exception"))
    implementation(project(":common:util"))
    implementation(libs.jackson.annotations)
}