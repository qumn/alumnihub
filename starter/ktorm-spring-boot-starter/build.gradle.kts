
plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.jdbc.starter)
    implementation(libs.ktorm.jackson)
    implementation(project(":commons:ktorm"))
}
