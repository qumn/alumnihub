package io.github.qumn.ktorm.interceptor

import io.github.qumn.ktorm.base.LOGICAL_DELETED_COLUMN
import org.ktorm.expression.*
import org.ktorm.schema.BooleanSqlType

/**
 * replace delete expression with update expression when the table enable logical deleted
 */
class LogicalDeleteInterceptor : SqlExpressionVisitorInterceptor {
    override fun intercept(expr: SqlExpression, visitor: SqlExpressionVisitor): SqlExpression {
        if (expr !is DeleteExpression)
            return expr
        val logicalColumnName = expr.table.extraProperties[LOGICAL_DELETED_COLUMN] as String? ?: return expr
        return UpdateExpression(
            table = expr.table,
            where = expr.where,
            assignments = listOf(
                ColumnAssignmentExpression(
                    column = ColumnExpression(table = expr.table, name = logicalColumnName, sqlType = BooleanSqlType),
                    expression = ArgumentExpression(value = true, sqlType = BooleanSqlType)
                )
            ),
            extraProperties = expr.extraProperties,
        )
    }
}