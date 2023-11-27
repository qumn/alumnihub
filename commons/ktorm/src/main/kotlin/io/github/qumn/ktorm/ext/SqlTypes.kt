package io.github.qumn.ktorm.ext

import org.ktorm.schema.BaseTable
import org.ktorm.schema.Column
import org.ktorm.schema.SqlType
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represent values of PostgreSQL `int[]` SQL type.
 */
public typealias IntArray = Array<Int>

/**
 * Define a column typed [TextArraySqlType].
 */
public fun BaseTable<*>.intArray(name: String): Column<IntArray> {
    return registerColumn(name, IntArraySqlType)
}

/**
 * [SqlType] implementation represents PostgreSQL `int[]` type.
 */
public object IntArraySqlType : SqlType<IntArray>(Types.ARRAY, "int[]") {

    override fun doSetParameter(ps: PreparedStatement, index: Int, parameter: IntArray) {
        ps.setObject(index, parameter)
    }

    @Suppress("UNCHECKED_CAST")
    override fun doGetResult(rs: ResultSet, index: Int): IntArray? {
        val sqlArray = rs.getArray(index) ?: return null
        try {
            val objectArray = sqlArray.array as Array<Any>?
            return objectArray?.map { it as Int }?.toTypedArray()
        } finally {
            sqlArray.free()
        }
    }
}



/**
 * Represent values of PostgreSQL `long[]` SQL type.
 */
public typealias LongArray = Array<Long?>

/**
 * Define a column typed [TextArraySqlType].
 */
public fun BaseTable<*>.longArray(name: String): Column<LongArray> {
    return registerColumn(name, LongArraySqlType)
}

/**
 * [SqlType] implementation represents PostgreSQL `int[]` type.
 */
public object LongArraySqlType : SqlType<LongArray>(Types.ARRAY, "bigint[]") {

    override fun doSetParameter(ps: PreparedStatement, index: Int, parameter: LongArray) {
        ps.setObject(index, parameter)
    }

    @Suppress("UNCHECKED_CAST")
    override fun doGetResult(rs: ResultSet, index: Int): LongArray? {
        val sqlArray = rs.getArray(index) ?: return null
        try {
            val objectArray = sqlArray.array as Array<Any>?
            return objectArray?.map { it as Long }?.toTypedArray()
        } finally {
            sqlArray.free()
        }
    }
}

