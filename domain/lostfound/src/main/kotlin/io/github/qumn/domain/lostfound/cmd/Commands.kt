package io.github.qumn.domain.lostfound.cmd

import io.github.qumn.domain.lostfound.model.LostFoundID
import io.github.qumn.domain.lostfound.model.MissingItem
import io.github.qumn.domain.lostfound.model.Question
import io.github.qumn.domain.system.api.user.model.UID

data class PublishMissingItemCmd(
    val publisherId: UID,
    val missingItem: MissingItem,
    val location: String,
//    val pickupTime: Instant,
    val questions: List<Question>,
)

data class ClaimCmd(
    val lostFoundID: LostFoundID,
    val ownerId: UID,
    val answers: List<String>,
)