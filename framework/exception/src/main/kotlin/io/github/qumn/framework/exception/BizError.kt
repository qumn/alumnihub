package io.github.qumn.framework.exception

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

data class BizNotAllowedError(
    override val msg: String = BizHttpStatus.NOT_ALLOWED.msg,
) : BizError {
    override val code: Int = BizHttpStatus.NOT_ALLOWED.code
}

data class BizArgumentError(
    override val msg: String = BizHttpStatus.ARGUMENT_ERROR.msg,
) : BizError {
    override val code: Int = BizHttpStatus.ARGUMENT_ERROR.code
}