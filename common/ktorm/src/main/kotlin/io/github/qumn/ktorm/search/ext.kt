package io.github.qumn.ktorm.search

import org.ktorm.entity.EntitySequence
import org.ktorm.entity.filter
import org.ktorm.schema.BaseTable
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.schema.ReferenceBinding
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField

public fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.search(searchDto: Any): EntitySequence<E, T> {
    val predicates = buildSearchPredicates(searchDto, sourceTable)
    var curr = this
    for (predicate in predicates) {
        curr = curr.filter { predicate }
    }
    return curr
}

public fun <D : Any, E : BaseTable<E>> MutableList<ColumnDeclaring<Boolean>>.search(
    searchDto: D,
    table: E,
) {
    this += buildSearchPredicates(searchDto, table)
}

data class SearchFieldCache(
    val property: KProperty1<*, *>,
    val operation: Operation,
    val operator: Operator<*, *>,
    val doOperatorFun: KFunction<ColumnDeclaring<Boolean>>,
)

private val propertyOperationCache =
    ConcurrentHashMap<KProperty1<*, *>, SearchFieldCache>()

private fun <D : Any, E : Any> buildSearchPredicates(
    searchDto: D,
    table: BaseTable<E>,
): List<ColumnDeclaring<Boolean>> {
    val searchConditions = mutableListOf<ColumnDeclaring<Boolean>>()

    @Suppress("UNCHECKED_CAST")
    val properties = searchDto::class.memberProperties.toList() as List<KProperty1<D, *>>

    for (property in properties) {
        // if the value of field is null, ignore the field
        if (property.get(searchDto) == null) continue
        val value = property.get(searchDto) as Any

        // get the operator instant and the doOperate function
        val (_, operation, operatorInst, doOperate) = getOperator(property)
        if (operatorInst is NOOP) continue

        val columnName = operation.columnName.ifBlank { property.name }
        val column =
            table.columns.find { it.name == columnName.toCamelCase() }
                ?: continue // can not find the column, continue

        // if the column is a nested reference, then build the search condition of nested table
        if (column.binding is ReferenceBinding) {
            val referenceTable = (column.binding as ReferenceBinding).referenceTable
            searchConditions += buildSearchPredicates(
                value,
                referenceTable
            )
            continue
        }

        doOperate.call(operatorInst, column, value).let {
            searchConditions.add(it)
        }
    }
    return searchConditions
}

private fun getOperator(property: KProperty1<*, *>): SearchFieldCache {
    if (!propertyOperationCache.containsKey(property)) {
        val operation = property.javaField?.getAnnotation(Operation::class.java)
            ?: property.findAnnotation<Operation>()
            ?: Operation(EQ::class) // if there not is Operation, use the EQ operation
        val operator = operation.operator
        val operatorInst = operator.primaryConstructor?.call()!!

        @Suppress("UNCHECKED_CAST")
        val doOperate = operator.memberFunctions.find { it.name == "doOperate" }
                as KFunction<ColumnDeclaring<Boolean>>
        propertyOperationCache[property] = SearchFieldCache(property, operation, operatorInst, doOperate)
    }
    return propertyOperationCache[property]!!
}

/**
 * transform to camelCase
 */
private fun String.toCamelCase(): String {
    // 替换中划线和下划线为空格，然后转为驼峰
    val words = this.split("-", "_")
        .map { it.replaceFirstChar(Char::titlecase) }
    return words.joinToString(separator = "").replaceFirstChar(Char::lowercase)
}