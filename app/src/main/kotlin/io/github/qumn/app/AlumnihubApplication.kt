package io.github.qumn.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication(scanBasePackages = ["io.github.qumn"])
class AlumnihubApplication

fun main(args: Array<String>) {
    runApplication<AlumnihubApplication>(*args)
}
