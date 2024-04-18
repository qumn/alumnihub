plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    api(libs.spring.boot.security.starter)
    api(libs.spring.boot.web.starter)
    implementation(libs.logging)
    implementation(libs.jjwt.api)
    implementation(project(":framework:web"))
    implementation(project(":domain:system:api"))
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)
}