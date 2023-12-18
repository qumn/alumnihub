package io.github.qumn.ktorm.ext;

import io.github.qumn.ktorm.dialet.ExtractFunction
import io.github.qumn.ktorm.dialet.FunctionExpressionWithOutBracket
import org.ktorm.expression.*
import org.ktorm.schema.*
import org.postgresql.util.PGInterval
import java.sql.*
import java.time.Instant

public fun currentDate(precision: Int? = null): FunctionExpressionWithOutBracket<Date> {
    assert(precision == null || precision in 0..6) { "the range of precision is zero to six" }
    return FunctionExpressionWithOutBracket(
        functionName = "current_date",
        arguments = precision?.let { listOf(ArgumentExpression(precision, IntSqlType)) } ?: emptyList(),
        sqlType = DateSqlType
    )
}

public fun currentTime(precision: Int? = null): FunctionExpressionWithOutBracket<Time> {
    assert(precision == null || precision in 0..6) { "the range of precision is zero to six" }
    return FunctionExpressionWithOutBracket(
        functionName = "current_time",
        arguments = precision?.let { listOf(ArgumentExpression(precision, IntSqlType)) } ?: emptyList(),
        sqlType = TimeSqlType
    )
}

public fun currentStamp(precision: Int? = null): FunctionExpressionWithOutBracket<Timestamp> {
    assert(precision == null || precision in 0..6) { "the range of precision is zero to six" }
    return FunctionExpressionWithOutBracket(
        functionName = "current_timestamp",
        arguments = precision?.let { listOf(ArgumentExpression(precision, IntSqlType)) } ?: emptyList(),
        sqlType = TimestampSqlType
    )
}


public object IntervalSqlType : SqlType<Interval>(Types.OTHER, "interval") {

    override fun doSetParameter(ps: PreparedStatement, index: Int, parameter: Interval) {
        ps.setObject(index, parameter.pgInterval)
    }

    override fun doGetResult(rs: ResultSet, index: Int): Interval? {
        return (rs.getObject(index) as? PGInterval)?.let { Interval(it) }
    }
}

class Interval(val pgInterval: PGInterval) {
    companion object {
        val YEAR = IntervalUnit.YEAR
        val MONTH = IntervalUnit.MONTH
        val WEEK = IntervalUnit.WEEK
        val DAY = IntervalUnit.DAY
        val HOUR = IntervalUnit.HOUR
        val MINUTE = IntervalUnit.MINUTE
        val SECOND = IntervalUnit.SECOND
    }

    constructor(
        years: Int = 0,
        months: Int = 0,
        days: Int = 0,
        hours: Int = 0,
        minutes: Int = 0,
        seconds: Double = 0.0,
    ) :
            this(PGInterval(years, months, days, hours, minutes, seconds))

    constructor(intervalString: String) : this(PGInterval(intervalString))


    val years: Int get() = pgInterval.years
    val months: Int get() = pgInterval.months
    val days: Int get() = pgInterval.days
    val hours: Int get() = pgInterval.hours
    val minutes: Int get() = pgInterval.minutes
    val seconds: Double get() = pgInterval.seconds

    override fun toString(): String = pgInterval.toString()
}

public infix operator fun Interval.minus(other: Interval): Interval {
    return Interval(
        years = this.years - other.years,
        months = this.months - other.months,
        days = this.days - other.days,
        hours = this.hours - other.days,
        minutes = this.minutes - other.minutes,
        seconds = this.seconds - other.seconds
    )
}

public infix operator fun Interval.plus(other: Interval): Interval {
    return Interval(
        years = this.years + other.years,
        months = this.months + other.months,
        days = this.days + other.days,
        hours = this.hours + other.days,
        minutes = this.minutes + other.minutes,
        seconds = this.seconds + other.seconds
    )
}

public enum class IntervalUnit {
    YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND
}

public fun extract(field: IntervalUnit, interval: Interval): ExtractFunction {
    return ExtractFunction(field, ArgumentExpression(interval, IntervalSqlType), IntSqlType)
}

public fun extract(field: IntervalUnit, timestamp: Timestamp): ExtractFunction {
    return ExtractFunction(field, ArgumentExpression(timestamp, TimestampSqlType), IntSqlType)
}


public fun extract(field: IntervalUnit, date: Date): ExtractFunction {
    return ExtractFunction(field, ArgumentExpression(date, DateSqlType), IntSqlType)
}

public fun extract(
    field: IntervalUnit,
    intervalColumn: ColumnDeclaring<java.util.Date>,
): ExtractFunction {
    return ExtractFunction(field, intervalColumn.asExpression(), IntSqlType)
}

@JvmName("extractInstant")
public fun extract(
    field: IntervalUnit,
    intervalColumn: ColumnDeclaring<Instant>,
): ExtractFunction {
    return ExtractFunction(field, intervalColumn.asExpression(), IntSqlType)
}


public fun dateTrunc(unit: IntervalUnit, timestamp: Timestamp): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            ArgumentExpression(timestamp, TimestampSqlType)
        ),
        sqlType = DateSqlType
    )
}

public fun dateTrunc(unit: IntervalUnit, date: Date): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            ArgumentExpression(date, DateSqlType)
        ),
        sqlType = DateSqlType
    )
}

public fun dateTrunc(unit: IntervalUnit, date: Interval): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            ArgumentExpression(date, IntervalSqlType)
        ),
        sqlType = DateSqlType
    )
}

public fun dateTrunc(
    unit: IntervalUnit,
    dateColumn: ColumnDeclaring<java.util.Date>,
): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            dateColumn.asExpression()
        ),
        sqlType = DateSqlType
    )
}

@JvmName("dateTruncInstant")
public fun dateTrunc(
    unit: IntervalUnit,
    dateColumn: ColumnDeclaring<Instant>,
): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            dateColumn.asExpression()
        ),
        sqlType = DateSqlType
    )
}

public fun <T : java.util.Date> dateTrunc(
    unit: IntervalUnit,
    dateColumn: ScalarExpression<T>,
): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            dateColumn.asExpression()
        ),
        sqlType = DateSqlType
    )
}

@JvmName("dateTruncInstant")
public fun dateTrunc(
    unit: IntervalUnit,
    dateColumn: ScalarExpression<Instant>,
): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            dateColumn.asExpression()
        ),
        sqlType = DateSqlType
    )
}

public fun <T : java.util.Date> dateTrunc(
    unit: IntervalUnit,
    date: T,
): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            CastingExpression(ArgumentExpression(Date(date.time), DateSqlType), DateSqlType)
        ),
        sqlType = DateSqlType
    )
}

@JvmName("dateTruncInstant")
public fun dateTrunc(
    unit: IntervalUnit,
    instant: Instant,
): FunctionExpression<Date> {
    return FunctionExpression(
        functionName = "date_trunc",
        arguments = listOf(
            ArgumentExpression(unit.name, VarcharSqlType),
            CastingExpression(ArgumentExpression(instant, InstantSqlType), InstantSqlType)
        ),
        sqlType = DateSqlType
    )
}


public fun toChar(
    instant: Instant,
    dateFormat: String,
): FunctionExpression<String> {
    return FunctionExpression(
        functionName = "to_char",
        arguments = listOf(
            CastingExpression(ArgumentExpression(value = instant, sqlType = InstantSqlType), InstantSqlType),
            ArgumentExpression(value = dateFormat, sqlType = VarcharSqlType)
        ),
        sqlType = VarcharSqlType
    )
}


public fun toChar(
    interval: Interval,
    dateFormat: String,
): FunctionExpression<String> {
    return FunctionExpression(
        functionName = "to_char",
        arguments = listOf(
            CastingExpression(ArgumentExpression(value = interval, sqlType = IntervalSqlType), IntervalSqlType),
            ArgumentExpression(value = dateFormat, sqlType = VarcharSqlType)
        ),
        sqlType = VarcharSqlType
    )
}

public fun <T : java.util.Date> toChar(
    dateColumn: ColumnDeclaring<T>,
    dateFormat: String,
): FunctionExpression<String> {
    return FunctionExpression(
        functionName = "to_char",
        arguments = listOf(
            dateColumn.asExpression(),
            ArgumentExpression(value = dateFormat, sqlType = VarcharSqlType)
        ),
        sqlType = VarcharSqlType
    )
}

@JvmName("toCharInstant")
public fun toChar(
    dateColumn: ColumnDeclaring<Instant>,
    dateFormat: String,
): FunctionExpression<String> {
    return FunctionExpression(
        functionName = "to_char",
        arguments = listOf(
            dateColumn.asExpression(),
            ArgumentExpression(value = dateFormat, sqlType = VarcharSqlType)
        ),
        sqlType = VarcharSqlType
    )
}

