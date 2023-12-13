/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package io.github.qumn.app

import org.ktorm.database.Database
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication(scanBasePackages = ["io.github.qumn"])
class KtAdmApplication

fun main(args: Array<String>) {
    runApplication<KtAdmApplication>(*args)
}


@RestController
class UserController(
    val database: Database,
) {
    @GetMapping
    fun hello(): String {
        return "hello1"
    }
}