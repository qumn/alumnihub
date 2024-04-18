plugins {
    id("io.github.qumn.domain-conventions")
}

dependencies {
    implementation(project(":starter:axon-spring-boot-starter"))
    implementation(project(":common:util"))
    implementation(project(":domain:comment:api"))
    implementation(project(":framework:storage"))
    implementation(project(":domain:system:api"))
}
