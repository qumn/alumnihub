plugins {
    id("io.github.qumn.domain-conventions")
}

dependencies {
    implementation(project(":domain:system:api"))
    implementation(project(":starter:axon-spring-boot-starter"))

    implementation(project(":common:util"))
}