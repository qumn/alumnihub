plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    api(libs.spring.boot.starter)
    api(libs.spring.boot.web.starter)
    implementation(libs.logging)
}