package io.github.qumn.framework.storage.ext

import io.github.qumn.framework.storage.model.URN
import org.ktorm.schema.BaseTable
import org.ktorm.schema.Column
import org.ktorm.schema.SqlType
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


/**
 * Define a column typed [urn].
 */
public fun BaseTable<*>.urn(name: String): Column<URN> {
    return registerColumn(name, URNSqlType)
}

/**
 * a custom varchar type
 */
public object URNSqlType : SqlType<URN>(Types.VARCHAR, "varchar") {

    override fun doSetParameter(ps: PreparedStatement, index: Int, parameter: URN) {
        ps.setObject(index, parameter)
    }

    override fun doGetResult(rs: ResultSet, index: Int): URN? {
        val urn = rs.getString(index) ?: return null
        return URN(urn)
    }
}

