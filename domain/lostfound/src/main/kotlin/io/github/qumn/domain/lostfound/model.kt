package io.github.qumn.domain.lostfound

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN


enum class LostFoundStatus {
    Missing,        // 丢失
    Found,          // 找到
    Returning,      // 归还中
    Returned,       // 已归还
}

data class LostFound(
    val id: Long,
    val publisherId: UID,
    val ownerId: UID,
    val status: LostFoundStatus,
    val location: String,
    val questions: List<Question>,
    val missionItem: MissionItem,
)

data class MissionItem(
    val desc: String,
    var imgs: List<URN>,
)

data class Question(
    val content: String,
    val answer: String,
)
