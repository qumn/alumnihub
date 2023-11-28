plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(libs.spring.boot.starter)
    testImplementation(libs.spring.boot.test.starter)
}