plugins {
    id("io.github.qumn.kotlin-library-conventions")
}


dependencies {
    api(libs.ktorm.core)
    api(libs.ktorm.postgresql)
    api(libs.ktorm.jackson)
    api(libs.postgresql)
    testImplementation(project(":common:test"))
    testRuntimeOnly(libs.h2)
}