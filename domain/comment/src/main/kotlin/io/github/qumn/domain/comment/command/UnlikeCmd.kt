package io.github.qumn.domain.comment.command

import io.github.qumn.domain.system.api.user.model.UID


data class UnlikeCmd(
    val cid: Long,
    val uid: UID
)