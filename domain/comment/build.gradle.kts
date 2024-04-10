plugins {
    id("io.github.qumn.domain-conventions")
}

dependencies {
    implementation(libs.logging)
    implementation(project(":domain:comment:api"))
    testImplementation(project(":domain:system:api"))
}