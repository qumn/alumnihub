plugins {
    id("io.github.qumn.domain-conventions")
}

dependencies {
    implementation(project(":domain:system:api"))
    implementation(libs.spring.boot.axon.starter)
}