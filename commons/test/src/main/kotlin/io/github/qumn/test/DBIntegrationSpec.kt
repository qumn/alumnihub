package io.github.qumn.test;

import io.github.qumn.test.config.DbTestAutoConfiguration
import io.kotest.core.extensions.Extension
import io.kotest.core.extensions.install
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.config.TestCaseConfig
import io.kotest.extensions.testcontainers.JdbcDatabaseContainerExtension
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer


// when inherit the class, the postgresql will start
@SpringBootTest(classes = [DbTestAutoConfiguration::class])
public abstract class DBIntegrationSpec(body: StringSpec.() -> Unit = {}) : StringSpec(body) {
}