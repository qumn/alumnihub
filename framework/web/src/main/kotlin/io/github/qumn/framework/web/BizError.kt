package io.github.qumn.framework.web

interface BizError {
    val code: Int
    val msg: String

    fun toThrow(): Nothing {
        throw BizException(code, msg)
    }
}
