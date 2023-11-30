import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion by extra("3.1.4")

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm")
    // all open config for spring annotation
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}


// import spring version controller
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        freeCompilerArgs += "-Xcontext-receivers"
        jvmTarget = "17"
    }
}