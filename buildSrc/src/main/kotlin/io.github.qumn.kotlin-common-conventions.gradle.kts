import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion by project.properties
val axonVersion by project.properties
val jacksonVersion by project.properties

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm")
    kotlin("plugin.serialization")
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
        mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
        mavenBom("org.axonframework:axon-bom:${axonVersion}")
    }
    dependencies {
        dependency("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
        dependency("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
        dependency("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
        dependency("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
        dependency("com.fasterxml.jackson.module:jackson-module-parameter-names:${jacksonVersion}")
    }
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

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