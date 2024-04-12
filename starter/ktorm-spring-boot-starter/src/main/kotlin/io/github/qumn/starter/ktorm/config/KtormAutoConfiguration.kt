package io.github.qumn.starter.ktorm.config

import io.github.qumn.ktorm.base.database
import io.github.qumn.ktorm.dialet.KtAdmPostgreSqlDialect
import io.github.qumn.ktorm.interceptor.*
import org.ktorm.database.Database
import org.ktorm.jackson.KtormModule
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import javax.sql.DataSource


@AutoConfiguration
class KtormAutoConfiguration(
    val dataSource: DataSource,
) {
    /**
     * config for database
     */
    @Bean
    fun dataBase(): Database {
        val interceptor = CompositorVisitorInterceptor()
        interceptor.register(LogicalSelectVisitorInterceptor())
            .register(LogicalDeleteInterceptor())
            .register(UpdateAutoFillVisitorInterceptor())
            .register(InsertAutoFillVisitorInterceptor())
        return Database.connectWithSpringSupport(
            dataSource,
            logger = ConsoleLogger(LogLevel.TRACE),
            dialect = KtAdmPostgreSqlDialect(interceptor)
        ).also {
            // set the global database variable
            database = it
        }
    }

    /**
     * Register Ktorm's Jackson extension to the Spring's container
     * so that we can serialize/deserialize Ktorm entities.
     */
    @Bean
    fun ktormModule(): KtormModule {
        return KtormModule()
    }
}