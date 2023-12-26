package io.github.qumn.framework.exception

enum class BizHttpStatus(val code: Int, val msg: String) {
    NOT_ALLOWED(460, "操作不被允许"),
    ARGUMENT_ERROR(461, "参数错误, 请检查参数"),
}