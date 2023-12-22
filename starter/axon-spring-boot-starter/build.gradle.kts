plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter)
    api("org.axonframework:axon-spring-boot-starter")
}