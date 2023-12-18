package io.github.qumn.ktorm.page

data class PageParam(
    val pageNo: Int = 1,
    val pageSize: Int = 10,
    val offset: Int =
        (pageNo - 1) * pageSize,
)

data class Page<T>(
    val list: List<T>,
    val total: Int,
    val pageNo: Int,
    val pageSize: Int,
    val offset: Int =
        (pageNo - 1) * pageSize,
    val totalPage: Int =
        if (total % pageSize == 0)
            total / pageSize
        else
            total / pageSize + 1,
) {
    companion object {
        fun <T> empty(pageNo: Int, pageSize: Int): Page<T> =
            Page(listOf(), 0, pageNo, pageSize)

        fun <T> of(list: List<T>, total: Int, pageNo: Int, pageSize: Int): Page<T> =
            Page(list, total, pageNo, pageSize)

        fun <T> of(list: List<T>, total: Int, pageParam: PageParam): Page<T> =
            Page(list, total, pageParam.pageNo, pageParam.pageSize)
    }
}