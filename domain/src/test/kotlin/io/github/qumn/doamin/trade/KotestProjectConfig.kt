package io.github.qumn.doamin.trade

import io.kotest.common.ExperimentalKotest
import io.kotest.core.config.AbstractProjectConfig

object KotestProjectConfig : AbstractProjectConfig() {
    override val parallelism = Runtime.getRuntime().availableProcessors()

    @ExperimentalKotest
    override val concurrentTests = 3

    @ExperimentalKotest
    override val concurrentSpecs = parallelism
}
