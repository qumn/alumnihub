plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(project(":common:test"))

    implementation(libs.spring.boot.security.starter)
    api(libs.spring.boot.starter)
    api(libs.spring.boot.web.starter)
}