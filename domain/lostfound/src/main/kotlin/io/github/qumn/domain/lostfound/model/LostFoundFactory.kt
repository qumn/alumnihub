package io.github.qumn.domain.lostfound.model

import io.github.qumn.domain.system.api.user.model.UID


object LostFoundFactory {
    fun createNew(publisherId: UID, location: String, questions: List<Question>, missingItem: MissingItem): LostFound {
        return LostFound(
            LostFoundID.generate(),
            publisherId,
            null,
            LostFoundStatus.Missing,
            location,
            questions,
            missingItem
        )
    }
}