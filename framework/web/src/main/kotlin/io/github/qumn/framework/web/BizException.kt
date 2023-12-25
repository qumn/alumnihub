package io.github.qumn.framework.web

class BizException(
    val code: Int,
    val msg: String,
) : RuntimeException()