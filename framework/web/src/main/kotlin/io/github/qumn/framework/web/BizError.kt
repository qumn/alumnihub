package io.github.qumn.framework.web

interface BizError {
    val code: Int
    val msg: String

    fun toThrow(): Nothing {
        when (this.code) {
            BizHttpStatus.NOT_ALLOWED.code -> throw BizNotAllowedException(this.msg)
            BizHttpStatus.ARGUMENT_ERROR.code -> throw BizArgumentException(this.msg)
            else -> throw BizException(this.code, this.msg)
        }
    }
}