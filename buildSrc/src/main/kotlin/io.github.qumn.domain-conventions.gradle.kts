plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":framework:test"))
    implementation(project(":framework:security"))
    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(project(":starter:axon-spring-boot-starter"))

    api("org.springframework.boot:spring-boot-starter")
    api("org.springframework.boot:spring-boot-starter-web")
}
