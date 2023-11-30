plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(project(":commons:test"))

    implementation(libs.spring.boot.starter)
    testImplementation(libs.spring.boot.test.starter)
}