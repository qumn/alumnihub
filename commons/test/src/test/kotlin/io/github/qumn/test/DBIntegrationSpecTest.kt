package io.github.qumn.test;

import javax.sql.DataSource

public class DBIntegrationSpecTest(
    dataSource: DataSource
) : DBIntegrationSpec({
    "spring construct di should work" {
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
