plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":framework:test"))
    implementation(project(":framework:security"))
    api(project(":framework:web"))
    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(project(":starter:axon-spring-boot-starter"))
}
