plugins {
    id("io.github.qumn.kotlin-library-conventions")
}


dependencies {
    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(project(":commons:util"))
    api(libs.spring.mockk)
    api("io.kotest:kotest-property-jvm:5.8.0")
    api(libs.spring.boot.test.starter) {
        exclude(module = "mockito-core")
    }
}
