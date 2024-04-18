package io.github.qumn.domain.comment.command

import io.github.qumn.domain.system.api.user.model.UID

data class LikeCmd(
    val cid: Long,
    val uid: UID
)