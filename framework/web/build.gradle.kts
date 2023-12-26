plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    api(libs.spring.boot.starter)
    api(libs.spring.boot.web.starter)
    api(project(":framework:exception"))
    implementation(libs.logging)
}