package io.github.qumn.domain.lostfound.query

import io.github.qumn.domain.lostfound.infrastructure.lostFounds
import io.github.qumn.domain.lostfound.infrastructure.toOverview
import io.github.qumn.domain.lostfound.model.LostFoundStatus
import io.github.qumn.domain.system.api.user.query.UserQuery
import io.github.qumn.ktorm.page.Page
import io.github.qumn.ktorm.page.PageParam
import io.github.qumn.ktorm.search.Operation
import io.github.qumn.ktorm.search.searchPage
import org.ktorm.database.Database
import org.ktorm.entity.sortedByDescending
import org.springframework.stereotype.Component
import java.time.Instant


class LostFoundSearchParam(
    pageNo: Int = 1,
    pageSize: Int = 10,
    isCount: Boolean = false,

    @Operation
    val status: LostFoundStatus = LostFoundStatus.Missing,
) : PageParam(pageNo, pageSize, isCount)


data class LostFoundOverview(
    val id: Long,
    val img: String,
    val title: String,
    val location: String,

    val questions: List<String>,
    val publisherId: Long,
    val publisherAvatar: String,
    val publisherName: String,
    val publishAt: Instant,
)


@Component
class LostFoundQueryHandler(
    val db: Database,
    val userQuery: UserQuery,
) {

    fun search(param: LostFoundSearchParam): Page<LostFoundOverview> {
        return db.lostFounds.sortedByDescending { it.id }.searchPage(param).transform {
            val publisher = userQuery.queryBy(it.publisherId)
            it.toOverview(publisher)
        }
    }
}