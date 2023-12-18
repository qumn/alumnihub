package io.github.qumn.doamin.trade

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.StringSpec

val logger = KotlinLogging.logger {}

class TradeDomain : StringSpec({
    "kotest should work" {
        logger.info { "kotest work fine" }
    }
})