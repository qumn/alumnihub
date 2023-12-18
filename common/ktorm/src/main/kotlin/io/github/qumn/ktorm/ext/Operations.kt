package io.github.qumn.ktorm.ext

import org.ktorm.expression.ArgumentExpression
import org.ktorm.expression.BinaryExpression
import org.ktorm.expression.BinaryExpressionType
import org.ktorm.expression.CastingExpression
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.schema.InstantSqlType
import org.ktorm.schema.IntSqlType
import java.time.Instant

public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.minus(expr: ColumnDeclaring<Int>): BinaryExpression<T> {
    return BinaryExpression(
        BinaryExpressionType.MINUS,
        asExpression(),
        expr.asExpression(),
        sqlType
    )
}

public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.minus(value: Int): BinaryExpression<T> {
    return this - ArgumentExpression(value, IntSqlType)
}

public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.plus(expr: ColumnDeclaring<Int>): BinaryExpression<T> {
    return BinaryExpression(
        BinaryExpressionType.PLUS,
        asExpression(),
        expr.asExpression(),
        sqlType
    )
}

public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.plus(value: Int): BinaryExpression<T> {
    return this + ArgumentExpression(value, IntSqlType)
}

@JvmName("minusInterval")
public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.minus(expr: ColumnDeclaring<Interval>): BinaryExpression<Instant> {
    return BinaryExpression(
        BinaryExpressionType.MINUS,
        asExpression(),
        expr.asExpression(),
        InstantSqlType
    )
}

public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.minus(value: Interval): BinaryExpression<Instant> {
    return this - ArgumentExpression(value, IntervalSqlType)
}

@JvmName("plusInterval")
public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.plus(expr: ColumnDeclaring<Interval>): BinaryExpression<Instant> {
    return BinaryExpression(
        BinaryExpressionType.PLUS,
        asExpression(),
        expr.asExpression(),
        InstantSqlType
    )
}

public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.plus(value: Interval): BinaryExpression<Instant> {
    return this + ArgumentExpression(value, IntervalSqlType)
}


@JvmName("dateMinusInterval")
public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.minus(expr: ColumnDeclaring<T>): BinaryExpression<Int> {
    return BinaryExpression(
        BinaryExpressionType.MINUS,
        asExpression(),
        expr.asExpression(),
        IntSqlType
    )
}

public infix operator fun <T : java.util.Date> ColumnDeclaring<T>.minus(value: T): BinaryExpression<Int> {
    return CastingExpression(this.asExpression(), sqlType) - CastingExpression(ArgumentExpression(value, sqlType), sqlType)
}


@JvmName("minusInstant")
public infix operator fun ColumnDeclaring<Instant>.minus(expr: ColumnDeclaring<Instant>): BinaryExpression<Interval> {
    return BinaryExpression(
        BinaryExpressionType.MINUS,
        asExpression(),
        expr.asExpression(),
        IntervalSqlType
    )
}

public infix operator fun ColumnDeclaring<Instant>.minus(value: Instant): BinaryExpression<Interval> {
    return this - ArgumentExpression(value, InstantSqlType)
}

@JvmName("instantMinusInterval")
public infix operator fun ColumnDeclaring<Instant>.minus(expr: ColumnDeclaring<Interval>): BinaryExpression<Instant> {
    return BinaryExpression(
        BinaryExpressionType.MINUS,
        asExpression(),
        expr.asExpression(),
        InstantSqlType
    )
}

@JvmName("instantMinusInterval")
public infix operator fun ColumnDeclaring<Instant>.minus(value: Interval): BinaryExpression<Instant> {
    return this - ArgumentExpression(value, IntervalSqlType)
}

@JvmName("instantPlusInterval")
public infix operator fun ColumnDeclaring<Instant>.plus(expr: ColumnDeclaring<Interval>): BinaryExpression<Instant> {
    return BinaryExpression(
        BinaryExpressionType.PLUS,
        asExpression(),
        expr.asExpression(),
        InstantSqlType
    )
}

@JvmName("instantPlusInterval")
public infix operator fun ColumnDeclaring<Instant>.plus(value: Interval): BinaryExpression<Instant> {
    return this + ArgumentExpression(value, IntervalSqlType)
}


@JvmName("intervalMinusInterval")
public infix operator fun ColumnDeclaring<Interval>.minus(expr: ColumnDeclaring<Interval>): BinaryExpression<Interval> {
    return BinaryExpression(
        BinaryExpressionType.MINUS,
        asExpression(),
        expr.asExpression(),
        IntervalSqlType
    )
}

@JvmName("intervalMinusInterval")
public infix operator fun ColumnDeclaring<Interval>.minus(value: Interval): BinaryExpression<Interval> {
    return this - wrapArgument(value);
}


@JvmName("intervalPlusInterval")
public infix operator fun ColumnDeclaring<Interval>.plus(expr: ColumnDeclaring<Interval>): BinaryExpression<Interval> {
    return BinaryExpression(
        BinaryExpressionType.PLUS,
        asExpression(),
        expr.asExpression(),
        IntervalSqlType
    )
}

@JvmName("intervalPlusInterval")
public infix operator fun ColumnDeclaring<Interval>.plus(value: Interval): BinaryExpression<Interval> {
    return this + wrapArgument(value)
}
