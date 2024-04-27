package io.github.qumn.domain.lostfound.model

import io.github.qumn.domain.system.api.user.model.UID
import io.github.qumn.framework.storage.model.URN
import io.github.qumn.util.id.IdUtil
import io.github.qumn.util.id.nextId

@JvmInline
value class LostFoundID(val value: Long) {
    companion object {
        fun generate(): LostFoundID =
            LostFoundID(IdUtil.nextId())
    }
}

enum class LostFoundStatus {
    Missing,        // 丢失
    Found,          // 找到
    Returning,      // 归还中
    Returned,       // 已归还
}

data class LostFound(
    val id: LostFoundID,
    val publisherId: UID,
    val ownerId: UID?,
    val status: LostFoundStatus,
    val location: String,
    val questions: List<Question>,
    val missingItem: MissingItem,
) {
    /**
     * if the user answer all the question return a new LostFound with found state
     * otherwise return a null
     */
    fun claim(answers: List<String>, ownerID: UID): LostFound? {
        if (
        // TODO separate seach state to self class
            this.status == LostFoundStatus.Missing &&
            this.questions.size == answers.size &&
            questions.map { it.answer }.containsAll(answers)
        ) {
            return this.copy(status = LostFoundStatus.Found, ownerId = ownerID)
        }
        return null;
    }

    fun returned(): LostFound {
        return this.copy(status = LostFoundStatus.Returned)
    }
}

data class MissingItem(
    val desc: String,
    var imgs: List<URN>,
)

data class Question(
    val content: String,
    val answer: String,
) {
    fun isRight(answer: String): Boolean =
        this.answer.trim().equals(answer.trim(), true)
}
