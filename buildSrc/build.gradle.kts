import java.util.*

/*
 * This file was generated by the Gradle 'init' task.
 */

val versions = mutableMapOf<String, String>()

File(projectDir.parentFile, "gradle.properties").inputStream().use {
    val properties = Properties()
    properties.load(it)
    listOf(
        "kotlin", "springBoot", "springDependencyManagement"
    ).forEach { library ->
        versions[library + "Version"] = properties.getProperty(library + "Version")
    }
}


plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions["kotlinVersion"]}")
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin:${versions["kotlinVersion"]}")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${versions["springBootVersion"]}")
    implementation("io.spring.dependency-management:io.spring.dependency-management.gradle.plugin:${versions["springDependencyManagementVersion"]}")
}
