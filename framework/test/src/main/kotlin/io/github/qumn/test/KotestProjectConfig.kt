package io.github.qumn.test

import io.kotest.common.ExperimentalKotest
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringAutowireConstructorExtension

class KotestProjectConfig : AbstractProjectConfig() {
    override val parallelism = Runtime.getRuntime().availableProcessors()

    @ExperimentalKotest
    override val concurrentTests = 3

    @ExperimentalKotest
    override val concurrentSpecs = parallelism
    override fun extensions() = listOf(SpringAutowireConstructorExtension)
}
