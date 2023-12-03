import io.github.qumn.ktorm.ext.*
import io.github.qumn.test.DBIntegrationSpec
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.entity.filter
import org.ktorm.expression.ArgumentExpression
import org.ktorm.schema.DateSqlType
import java.sql.Date
import java.time.Instant
import java.util.*

public class OperationTest(database: Database) : DBIntegrationSpec({
    "date trunc date value should work" {
        database.from(BaseTest.Departments).select(
            dateTrunc(Interval.DAY, Date())
        ).map {
            println(it.getDate(1))
        }
    }

    "date trunc instant value shouldWork" {
        database.departments
            .filter { it.name like "zs" }
            .totalRecordsInAllPages
        database.from(BaseTest.Departments).select(
            dateTrunc(Interval.DAY, Instant.now())
        ).map {
            println(it.getDate(1))
        }
    }

    "date trunc instant column should work" {
        database.from(BaseTest.Departments).select(
            dateTrunc(Interval.DAY, BaseTest.Departments.createdAt)
        ).map {
            println(it.getDate(1))
        }
    }

    "to char instant value should work" {
        database.from(BaseTest.Departments).select(
            toChar(Instant.now(), "YYYY-MM-DD")
        ).map {
            println(it.getString(1))
        }
    }

    "to char interval value should work" {
        database.from(BaseTest.Departments).select(
            toChar(Interval(years = 22, months = 11, days = 3, hours = 3), "YYYY-MM-DD")
        ).map {
            println(it.getString(1))
        }
    }

    "to char interval column should work" {
        database.from(BaseTest.Departments).select(
            toChar(BaseTest.Departments.createdAt, "YYYY-MM-DD")
        ).map {
            println(it.getString(1))
        }
    }

    "date minus int should word" {
        database.from(BaseTest.Departments).select(
            currentDate() - 3
        ).map {
            println(it.getDate(1))
        }
    }

    "date minus interval should word" {
        database.from(BaseTest.Departments).select(
            currentStamp() - Interval(years = 2, months = 3, days = 4, hours = 5)
        ).map {
            println(it.getInstant(1))
        }
    }

    "instant minus instant should word" {
        database.from(BaseTest.Departments).select(
            BaseTest.Departments.createdAt - (BaseTest.Departments.createdAt - Interval(days = 2))
        ).map {
            println(it.getString(1))
        }
    }


    "interval plus interval should work" {
        database.from(BaseTest.Departments).select(
            ArgumentExpression(Interval(days = 2), IntervalSqlType) + Interval(days = 2) + Interval(days = 2),
            BaseTest.Departments.createdAt + ArgumentExpression(
                Interval(days = 2),
                IntervalSqlType
            ) + Interval(days = 2) + Interval(days = 2)
        ).map {
            println(it.getString(1))
            println(it.getString(2))
        }
    }

    "date minus date should work" {
        database.from(BaseTest.Departments).select(
            ArgumentExpression(Date(Date().time), DateSqlType) - java.sql.Date(Date().time)
        ).map {
            println(it.getInt(1))
        }
    }
})