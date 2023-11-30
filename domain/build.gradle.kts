import org.gradle.internal.classpath.Instrumented.systemProperty

plugins {
    id("io.github.qumn.kotlin-library-conventions")
}

dependencies {
    implementation(project(":commons:util"))
    implementation(project(":framework"))
    implementation(project(":starter:ktorm-spring-boot-starter"))

    testImplementation(project(":commons:test"))
}

systemProperty("junit.jupiter.execution.parallel.enabled", "true")
systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")