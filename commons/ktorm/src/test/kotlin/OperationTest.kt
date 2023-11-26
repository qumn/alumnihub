import io.github.qumn.ktorm.ext.*
import org.junit.jupiter.api.Test
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

public class OperationTest : BaseTest() {

    @Test
    fun dateTruncDateValueShouldWork() {
        database.from(Departments).select(
            dateTrunc(Interval.DAY, Date())
        ).map {
            println(it.getDate(1))
        }
    }

    @Test
    fun dateTruncInstantValueShouldWork() {
        database.departments
            .filter { it.name like "zs" }
            .totalRecordsInAllPages
        database.from(Departments).select(
            dateTrunc(Interval.DAY, Instant.now())
        ).map {
            println(it.getDate(1))
        }
    }

    @Test
    fun dateTruncInstantColumnShouldWork() {
        database.from(Departments).select(
            dateTrunc(Interval.DAY, Departments.createdAt)
        ).map {
            println(it.getDate(1))
        }
    }

    @Test
    fun toCharInstantValueShouldWork() {
        database.from(Departments).select(
            toChar(Instant.now(), "YYYY-MM-DD")
        ).map {
            println(it.getString(1))
        }
    }

    @Test
    fun toCharIntervalValueShouldWork() {
        database.from(Departments).select(
            toChar(Interval(years = 22, months = 11, days = 3, hours = 3), "YYYY-MM-DD")
        ).map {
            println(it.getString(1))
        }
    }

    @Test
    fun toCharIntervalColumnShouldWork() {
        database.from(Departments).select(
            toChar(Departments.createdAt, "YYYY-MM-DD")
        ).map {
            println(it.getString(1))
        }
    }

    @Test
    fun dateMinusIntShouldWord() {
        database.from(Departments).select(
            currentDate() - 3
        ).map {
            println(it.getDate(1))
        }
    }

    @Test
    fun dateMinusIntervalShouldWord() {
        database.from(Departments).select(
            currentStamp() - Interval(years = 2, months = 3, days = 4, hours = 5)
        ).map {
            println(it.getInstant(1))
        }
    }

    @Test
    fun instantMinusInstantShouldWord() {
        database.from(Departments).select(
            Departments.createdAt - (Departments.createdAt - Interval(days = 2))
        ).map {
            println(it.getString(1))
        }
    }


    @Test
    fun intervalPlusIntervalShouldWork() {
        database.from(Departments).select(
            ArgumentExpression(Interval(days = 2), IntervalSqlType) + Interval(days = 2) + Interval(days = 2),
            Departments.createdAt + ArgumentExpression(
                Interval(days = 2),
                IntervalSqlType
            ) + Interval(days = 2) + Interval(days = 2)
        ).map {
            println(it.getString(1))
            println(it.getString(2))
        }
    }

    @Test
    fun dateMinusDateShouldWork() {
        database.from(Departments).select(
            ArgumentExpression(Date(Date().time), DateSqlType) - java.sql.Date(Date().time)
        ).map {
            println(it.getInt(1))
        }
    }
}
