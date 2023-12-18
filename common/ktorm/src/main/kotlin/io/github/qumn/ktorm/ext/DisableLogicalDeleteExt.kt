package io.github.qumn.ktorm.ext

import io.github.qumn.ktorm.base.BaseTable
import io.github.qumn.ktorm.base.LOGICAL_DELETED_COLUMN
import org.ktorm.dsl.Query
import org.ktorm.entity.EntitySequence
import org.ktorm.expression.*

// template disable the logical delete for sequence api
fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.disableLogicalDeleted(): EntitySequence<E, T> {
    return this.withExpression(
        expression = this.expression.copy(
            from = disableLogicalDeletedHelper(
                this.expression.from,
                this.sourceTable.tableName
            )
        )
    )
}


// template disable the logical delete for dsl api
fun Query.disableLogicalDeleted(): Query {
    return this.withExpression(
        when (expression) {
            is SelectExpression -> {
                val selectExpression = expression as SelectExpression
                selectExpression.copy(from = disableLogicalDeletedHelper(selectExpression.from, null))
            }

            is UnionExpression -> throw IllegalStateException("logical delete is not supported in a union expression.")
        }
    )
}

/**
 * recursive the LOGICAL_DELETED_COLUMN key in extraProperties of tableExpression
 * @param tableName if the table name is not null remove properties of the tableExpression naming the `tableName`
 *                  otherwise remove the properties of all tableExpression
 */
private fun disableLogicalDeletedHelper(expression: QuerySourceExpression, tableName: String?): QuerySourceExpression {
    return when (expression) {
        is TableExpression -> {
            if (tableName == null || tableName == expression.name) {
                expression.copy(
                    extraProperties =
                    expression.extraProperties.filter { it.key != LOGICAL_DELETED_COLUMN }
                )
            } else {
                expression
            }
        }

        is JoinExpression -> {
            expression.copy(
                left = disableLogicalDeletedHelper(expression.left, tableName),
                right = disableLogicalDeletedHelper(expression.right, tableName)
            )
        }

        else -> expression
    }
}
