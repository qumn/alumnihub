plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    api(libs.spring.boot.security.starter)
    api(libs.spring.boot.web.starter)
    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)
}