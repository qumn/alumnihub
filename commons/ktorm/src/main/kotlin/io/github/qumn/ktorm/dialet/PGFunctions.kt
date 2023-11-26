package io.github.qumn.ktorm.dialet

import io.github.qumn.ktorm.ext.IntervalUnit
import org.ktorm.expression.ScalarExpression
import org.ktorm.schema.IntSqlType
import org.ktorm.schema.SqlType

/**
 * Function expression, represents a normal SQL function call.
 * if the arguments is empty, then format without bracket
 * e.g. current_time, current_timestamp
 *
 * @property functionName the name of the SQL function.
 * @property arguments arguments passed to the function.
 */
public data class FunctionExpressionWithOutBracket<T : Any>(
    val functionName: String,
    val arguments: List<ScalarExpression<*>>,
    override val sqlType: SqlType<T>,
    override val isLeafNode: Boolean = false,
    override val extraProperties: Map<String, Any> = emptyMap(),
) : ScalarExpression<T>()



/**
 * EXTRACT(field FROM source)
 */
public data class ExtractFunction(
    val field: IntervalUnit,
    val argument: ScalarExpression<*>,
    override val sqlType: IntSqlType,
    override val isLeafNode: Boolean = false,
    override val extraProperties: Map<String, Any> = emptyMap(),
) : ScalarExpression<Int>()