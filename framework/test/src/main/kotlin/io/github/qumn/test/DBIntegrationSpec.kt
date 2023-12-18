package io.github.qumn.test;

import io.github.qumn.test.config.DbTestAutoConfiguration
import io.kotest.core.spec.style.StringSpec
import org.springframework.test.context.ContextConfiguration


// when inherit the class, the postgresql will start
@ContextConfiguration(classes = [DbTestAutoConfiguration::class])
public abstract class DBIntegrationSpec(body: StringSpec.() -> Unit = {}) : StringSpec(body) {
}