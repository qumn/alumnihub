plugins {
    id("io.github.qumn.kotlin-library-conventions")
}


dependencies {
    api(libs.ktorm.core)
    api(libs.ktorm.postgresql)
    api(libs.postgresql)
    testRuntimeOnly(libs.h2)
}