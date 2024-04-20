package io.github.qumn.ktorm.page

open class PageParam(
    val pageNo: Int = 1,
    val pageSize: Int = 10,
    val isCount: Boolean = false,
) {
    val offset: Int
        get() = (pageNo - 1) * pageSize
}

data class Page<T>(
    val list: List<T>,
    val pageNo: Int,
    val pageSize: Int,
    val total: Long?,
    val totalPage: Long? =
        total?.let { total ->
            if ((total % pageSize) == 0L)
                total / pageSize
            else
                total / pageSize + 1L
        },
) {
    companion object {
        fun <T> empty(pageNo: Int, pageSize: Int): Page<T> =
            Page(listOf(), pageNo, pageSize, null)

        fun <T> from(list: List<T>, pageNo: Int, pageSize: Int, total: Long): Page<T> =
            Page(list, pageNo, pageSize, total)

        fun <T> from(list: List<T>, pageParam: PageParam, total: Long?): Page<T> =
            Page(list, pageParam.pageNo, pageParam.pageSize, total)
    }

    fun <R> transform(to: (T) -> R): Page<R> {
        return Page(this.list.map { to(it) }, this.pageNo, this.pageSize, this.total)
    }
}


open class IdPageParam<I : Comparable<I>>(
    val minId: I,
    val pageSize: Int = 10,
)

data class IdPage<T, I>(
    val list: List<T>,
    val pageSize: Int,
    val hasNext: Boolean = list.size == pageSize,
) {
    init {
        require(pageSize >= list.size) {
            "page size should >= the size of list"
        }
    }

    companion object {
        fun <T, I> empty(pageSize: Int = 10): IdPage<T, I> =
            IdPage(listOf(), pageSize)

        fun <T, I : Comparable<I>> from(list: List<T>, idPageParam: IdPageParam<I>): IdPage<T, I> =
            from(list, idPageParam.pageSize)

        fun <T, I : Comparable<I>> from(list: List<T>, pageSize: Int): IdPage<T, I> =
            IdPage(list, pageSize)

    }
}