plugins {
    id("io.github.qumn.domain-conventions")
}

dependencies {
    implementation(libs.logging)
    implementation(project(":domain:comment:api"))
    implementation(project(":common:util"))
    implementation(project(":domain:system:api"))
}