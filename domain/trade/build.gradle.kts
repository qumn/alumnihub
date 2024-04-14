import org.gradle.internal.classpath.Instrumented.systemProperty

plugins {
    id("io.github.qumn.domain-conventions")
}

dependencies {
    implementation(libs.logging)
    implementation(project(":common:util"))
    implementation(project(":domain:system:api"))
    implementation(project(":domain:comment:api"))
    implementation(project(":framework:storage"))
}

systemProperty("junit.jupiter.execution.parallel.enabled", "true")
systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")