package io.github.qumn.ktorm.search;

import org.ktorm.dsl.*
import org.ktorm.schema.Column
import org.ktorm.schema.ColumnDeclaring
import java.rmi.UnexpectedException
import kotlin.reflect.KClass

/**
 * @param operator the operator be used on the field
 * @param columnName if it's blank, then use the field name, otherwise use it
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Operation(val operator: KClass<out Operator<*, *>> = EQ::class, val columnName: String = "")


interface Operator<T : Any, V> {
    fun doOperate(column: Column<T>, value: V): ColumnDeclaring<Boolean>
}

class NOOP : Operator<Any, Any> {
    override fun doOperate(column: Column<Any>, value: Any): ColumnDeclaring<Boolean> {
        throw UnexpectedException("the function should never be called")
    }
}

class EQ : Operator<Any, Any> {
    override fun doOperate(column: Column<Any>, value: Any): ColumnDeclaring<Boolean> {
        return column eq value
    }
}

class GT<T : Comparable<T>> : Operator<T, T> {
    override fun doOperate(column: Column<T>, value: T): ColumnDeclaring<Boolean> {
        return column gt value
    }
}

class GTE<T : Comparable<T>> : Operator<T, T> {
    override fun doOperate(column: Column<T>, value: T): ColumnDeclaring<Boolean> {
        return column gte value
    }
}

class LT<T : Comparable<T>> : Operator<T, T> {
    override fun doOperate(column: Column<T>, value: T): ColumnDeclaring<Boolean> {
        return column lt value
    }
}

class LTE<T : Comparable<T>> : Operator<T, T> {
    override fun doOperate(column: Column<T>, value: T): ColumnDeclaring<Boolean> {
        return column lte value
    }
}

class LIKE : Operator<Any, Any> {
    override fun doOperate(column: Column<Any>, value: Any): ColumnDeclaring<Boolean> {
        return column like "%$value%"
    }
}

class NOTLIKE : Operator<Any, Any> {
    override fun doOperate(column: Column<Any>, value: Any): ColumnDeclaring<Boolean> {
        return column notLike "%$value%"
    }
}

class LLIKE : Operator<Any, Any> {
    override fun doOperate(column: Column<Any>, value: Any): ColumnDeclaring<Boolean> {
        return column like "%$value"
    }
}

class RLIKE : Operator<Any, Any> {
    override fun doOperate(column: Column<Any>, value: Any): ColumnDeclaring<Boolean> {
        return column like "$value%"
    }
}

/**
 * BETWEEN operator, if the first value is null then use gte, if the second value is null then use lte
 */
class BETWEEN<T : Comparable<T>> : Operator<T, Any> {
    @Suppress("UNCHECKED_CAST")
    override fun doOperate(column: Column<T>, value: Any): ColumnDeclaring<Boolean> {
        return when (value) {
            is Array<*> -> {
                assert(value[0] != null || value[1] != null) { "BETWEEN operator requires least one non-null value" }
                buildPredicate(column, value[0] as T?, value[1] as T?)
            }

            is Pair<*, *> -> {
                assert(value.first != null || value.second != null) { "BETWEEN operator requires least one non-null value" }
                buildPredicate(column, value.first as T?, value.second as T?)
            }

            is ClosedRange<*> -> {
                buildPredicate(column, value.start as T?, value.endInclusive as T?)
            }

            is Iterable<*> -> {
                buildPredicate(column, value.first() as T?, value.last() as T?)
            }

            else -> {
                throw IllegalArgumentException("BETWEEN only support Array, Pair, ClosedRange and Iterable")
            }
        }
    }

    private fun buildPredicate(column: Column<T>, value1: T?, value2: T?): ColumnDeclaring<Boolean> {
        return if (value1 != null && value2 != null) {
            column between value1..value2
        } else if (value1 == null && value2 != null) {
            column lte value2
        } else if (value1 != null && value2 == null) {
            column gte value1
        } else {
            throw java.lang.IllegalArgumentException("BETWEEN operator requires least one non-null value")
        }
    }
}

/**
 * inList operator
 */
class INLIST<T : Any> : Operator<T, Any> {
    @Suppress("UNCHECKED_CAST")
    override fun doOperate(column: Column<T>, value: Any): ColumnDeclaring<Boolean> {
        return when (value) {
            is Array<*> -> {
                column inList value.toList() as Collection<T>
            }

            is Pair<*, *> -> {
                column inList listOf(value.first, value.second) as Collection<T>
            }

            is ClosedRange<*> -> {
                column inList listOf(value.start, value.endInclusive) as Collection<T>
            }

            is Iterable<*> -> {
                column inList value.toList() as Collection<T>
            }

            else -> {
                throw IllegalArgumentException("BETWEEN only support Array, Pair, ClosedRange and Iterable")
            }
        }
    }
}

/**
 * inList operator
 */
class NOTINLIST<T : Any> : Operator<T, Any> {
    @Suppress("UNCHECKED_CAST")
    override fun doOperate(column: Column<T>, value: Any): ColumnDeclaring<Boolean> {
        return when (value) {
            is Array<*> -> {
                column notInList value.toList() as Collection<T>
            }

            is Pair<*, *> -> {
                column notInList listOf(value.first, value.second) as Collection<T>
            }

            is ClosedRange<*> -> {
                column notInList listOf(value.start, value.endInclusive) as Collection<T>
            }

            is Iterable<*> -> {
                column notInList value.toList() as Collection<T>
            }

            else -> {
                throw IllegalArgumentException("BETWEEN only support Array, Pair, ClosedRange and Iterable")
            }
        }
    }
}
