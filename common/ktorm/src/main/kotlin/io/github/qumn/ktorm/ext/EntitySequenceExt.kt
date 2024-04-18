package io.github.qumn.ktorm.ext

import io.github.qumn.ktorm.base.BaseTable
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.filter
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.take
import org.ktorm.schema.ColumnDeclaring

fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.exist(predicate: (T) -> ColumnDeclaring<Boolean>): Boolean {
    return this.filter(predicate).take(1).firstOrNull() != null
}