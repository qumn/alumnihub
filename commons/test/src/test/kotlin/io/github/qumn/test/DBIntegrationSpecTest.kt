package io.github.qumn.test;

import io.github.oshai.kotlinlogging.KotlinLogging
import javax.sql.DataSource

public class DBIntegrationSpecTest(
    dataSource: DataSource,
) : DBIntegrationSpec({
    val logger = KotlinLogging.logger {}
    "spring construct di should work" {
        logger.info { "hello" }
    }
    "DBIntegrationSpec should work" {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery("select version() as version").use { resultSet ->
                    while (resultSet.next()) {
                        println("DBIntegrationSpec work fine with db " + resultSet.getString("version"))
                    }
                }
            }
        }
    }
})
