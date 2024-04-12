package io.github.qumn.domain.comment.command

data class UnlikeCmd(
    val cid: Long,
    val uid: Long
)