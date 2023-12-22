package io.github.qumn.test.config

import io.github.qumn.starter.ktorm.config.KtormAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer

@TestConfiguration
@Import(
    value = [
        KtormAutoConfiguration::class,
        DataSourceAutoConfiguration::class,
    ]
)
public class DbTestAutoConfiguration{
    companion object {
        private val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:14").apply {
            withInitScript("init.sql")
        }

        init {
            // first start the container, before SpringAutowireConstructorExtension
            postgres.start()
            // set the config of spring datasource to tell spring how to connect to the postgres container
            System.setProperty("spring.datasource.url", postgres.jdbcUrl)
            System.setProperty("spring.datasource.username", postgres.username)
            System.setProperty("spring.datasource.password", postgres.password)
        }
    }
}
