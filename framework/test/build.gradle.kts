plugins {
    id("io.github.qumn.kotlin-library-conventions")
}
// define testcontainers version


dependencies {
    // kotest
    api(libs.kotest.junit5)
    api(libs.kotest.property)
    implementation(libs.kotest.extensions.spring)
    implementation(libs.logging)
    // test container
    implementation(libs.kotest.extensions.testcontainers)
    implementation(libs.testcontainers.postgresql)

    api(libs.spring.mockk)
    api(libs.spring.boot.test.starter) {
        exclude(module = "mockito-core")
    }

    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(project(":common:util"))
}
