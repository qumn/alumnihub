/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 * For more detailed information on multi-project builds, please refer to https://docs.gradle.org/8.3/userguide/building_swift_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "alumnihub"
include("app")

include("domain:comment")
include("domain:comment:api")
include("domain:trade")
include("domain:forum")
include("domain:system")
include("domain:system:api")

include("common:ktorm")
include("common:util")

include("framework:security")
include("framework:storage")
include("framework:test")
include("framework:web")
include("framework:exception")

include("starter:ktorm-spring-boot-starter")
include("starter:axon-spring-boot-starter")


val ktormVersion: String by settings
val testcontainersVersion: String by settings
val kotestVersion: String by settings
val kotestExtensionSpringVersion: String by settings
val kotestExtensionTestContainersVersion: String by settings
val kotlinLoggingVersion: String by settings
val springmockkVersion: String by settings
val axonVersion: String by settings

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // spring boot
            library("spring-boot-starter", "org.springframework.boot", "spring-boot-starter").withoutVersion()
            library("spring-boot-web-starter", "org.springframework.boot", "spring-boot-starter-web").withoutVersion()
            library(
                "spring-boot-jdbc-starter",
                "org.springframework.boot",
                "spring-boot-starter-data-jdbc"
            ).withoutVersion()
            library("spring-boot-test-starter", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library(
                "spring-boot-testcontainers",
                "org.springframework.boot",
                "spring-boot-testcontainers"
            ).withoutVersion()
            library(
                "spring-boot-security-starter",
                "org.springframework.boot",
                "spring-boot-starter-security"
            ).withoutVersion()
            library("jjwt-api", "io.jsonwebtoken", "jjwt-api").version("0.12.3")
            library("jjwt-impl", "io.jsonwebtoken", "jjwt-impl").version("0.12.3")
            library("jjwt-jackson", "io.jsonwebtoken", "jjwt-jackson").version("0.12.3")
            // axon
            library("spring-boot-axon-starter", "org.axonframework", "axon-spring-boot-starter").withoutVersion()

            library("jackson-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin").withoutVersion()

            // ktorm
            library("ktorm-core", "org.ktorm", "ktorm-core").version(ktormVersion)
            library("ktorm-mysql", "org.ktorm", "ktorm-support-mysql").version(ktormVersion)
            library("ktorm-postgresql", "org.ktorm", "ktorm-support-postgresql").version(ktormVersion)
            library("ktorm-jackson", "org.ktorm", "ktorm-jackson").version(ktormVersion)

            // test
            //library("mockk", "io.mockk", "mockk").version("1.13.6")
            // kotest
            library("kotest-junit5", "io.kotest", "kotest-runner-junit5-jvm").version(kotestVersion)
            library("kotest-property", "io.kotest", "kotest-property-jvm").version(kotestVersion)
            library("kotest-extensions-spring", "io.kotest.extensions", "kotest-extensions-spring").version(
                kotestExtensionSpringVersion
            )
            library(
                "kotest-extensions-testcontainers",
                "io.kotest.extensions",
                "kotest-extensions-testcontainers"
            ).version(kotestExtensionTestContainersVersion)
            library("spring-mockk", "com.ninja-squad", "springmockk").version(springmockkVersion)

            // TestContainers
            library("testcontainers-core", "org.testcontainers", "testcontainers").version(testcontainersVersion)
            library("testcontainers-postgresql", "org.testcontainers", "postgresql").version(testcontainersVersion)
            library("testcontainers-junit", "org.testcontainers", "junit-jupiter").version(testcontainersVersion)

            // database
            library("h2", "com.h2database", "h2").withoutVersion()
            library("postgresql", "org.postgresql", "postgresql").withoutVersion()
            // logging
            library("logging", "io.github.oshai", "kotlin-logging-jvm").version(kotlinLoggingVersion)
            // jackson
            library("jackson-annotations", "com.fasterxml.jackson.core", "jackson-annotations").withoutVersion()
            library("jackson-core", "com.fasterxml.jackson.core", "jackson-core").withoutVersion()
        }
    }
}
