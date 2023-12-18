
plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.jdbc.starter)
    api(libs.ktorm.jackson)
    api(project(":common:ktorm"))
}
