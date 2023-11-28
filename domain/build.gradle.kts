plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":commons:ktorm"))
    implementation(project(":framework"))
    implementation(project(":starter:ktorm-spring-boot-starter"))
    runtimeOnly(libs.postgresql)
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.web.starter)
    implementation(libs.jackson.kotlin)

    testImplementation(project(":framework"))
    testImplementation(libs.spring.boot.test.starter) {
        exclude(module = "mockito-core")
    }
//    testImplementation(libs.mockk)
    testImplementation(libs.spring.mockk)
}
