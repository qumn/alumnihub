package io.github.qumn.domain.comment.command

data class LikeCmd(
    val cid: Long,
    val uid: Long
)