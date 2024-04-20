package io.github.qumn.ktorm.page

import org.ktorm.entity.EntitySequence
import org.ktorm.entity.drop
import org.ktorm.entity.take
import org.ktorm.entity.toList
import org.ktorm.schema.BaseTable

public fun <E : Any, T : BaseTable<E>> EntitySequence<E, T>.page(
    pageParam: PageParam,
): Page<E> {
    val sequence = this.drop(pageParam.offset).take(pageParam.pageSize)
    val total = if (pageParam.isCount) {
        sequence.totalRecordsInAllPages.toLong()
    } else {
        null
    }
    return Page.from(sequence.toList(), pageParam, total)
}
