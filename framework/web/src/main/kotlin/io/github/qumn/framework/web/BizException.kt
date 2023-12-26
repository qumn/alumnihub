package io.github.qumn.framework.web

open class BizException(
    open val code: Int,
    open val msg: String,
) : RuntimeException()


class BizNotAllowedException(
    override val msg: String = BizHttpStatus.NOT_ALLOWED.msg,
) : BizException(BizHttpStatus.NOT_ALLOWED.code, msg)

class BizArgumentException(
    override val msg: String = BizHttpStatus.ARGUMENT_ERROR.msg,
) : BizException(BizHttpStatus.ARGUMENT_ERROR.code, msg)
