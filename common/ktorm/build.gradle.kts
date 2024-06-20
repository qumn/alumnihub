plugins {
    id("io.github.qumn.kotlin-library-conventions")
}


dependencies {
    api(libs.ktorm.core)
    api(libs.ktorm.postgresql)
    api(libs.ktorm.jackson)
    api(libs.ktorm.ksp.annotation)
    api(libs.postgresql)
    testImplementation(project(":framework:test"))
    testRuntimeOnly(libs.h2)
}