package io.github.qumn.test;

import io.github.qumn.test.config.DbTestAutoConfiguration
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer


@SpringBootTest(classes = [DbTestAutoConfiguration::class])
public abstract class DBIntegrationSpec(body: StringSpec.() -> Unit = {}) : StringSpec(body) {
    companion object {
        private val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:14").apply {
            withInitScript("init.sql")
            withReuse(true)
        }

        init {
            // when then DBIntegrationSpec be loaded, the postgres container will be started
            postgres.start()
            // set the config of spring datasource to tell spring how to connect to the postgres container
            System.setProperty("spring.datasource.url", postgres.jdbcUrl)
            System.setProperty("spring.datasource.username", postgres.username)
            System.setProperty("spring.datasource.password", postgres.password)
        }
    }

    init {
        // the enable the spring extension
        register(SpringExtension)
    }
}