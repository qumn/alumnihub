package io.github.qumn.ktorm.page

import org.ktorm.entity.EntitySequence
import org.ktorm.entity.drop
import org.ktorm.entity.take
import org.ktorm.entity.toList
import org.ktorm.schema.BaseTable

public fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.page(pageParam: PageParam): PageRst<E> {
    val sequence = this.drop(pageParam.offset).take(pageParam.pageSize)
    return PageRst.of(sequence.toList(), sequence.totalRecordsInAllPages, pageParam)
}
