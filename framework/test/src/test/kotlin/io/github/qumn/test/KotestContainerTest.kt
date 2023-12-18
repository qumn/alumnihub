package io.github.qumn.test;

import io.kotest.core.spec.style.StringSpec

public class KotestContainerTest(
) : StringSpec({
    "kotest should work" {
        println("work fine")
    }
})