package io.github.qumn.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackages = ["io.github.qumn"])
class AlumnihubApplication

fun main(args: Array<String>) {
    runApplication<AlumnihubApplication>(*args)
}
