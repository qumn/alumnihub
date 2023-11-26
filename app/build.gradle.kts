plugins {
    id("io.github.qumn.kotlin-application-conventions")
}

dependencies {
    implementation(project(":commons:ktorm"))
    implementation(project(":framework"))
    implementation(project(":starter:ktorm-spring-boot-starter"))
    runtimeOnly(libs.postgresql)
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.web.starter)
    implementation(libs.jackson.kotlin)
}