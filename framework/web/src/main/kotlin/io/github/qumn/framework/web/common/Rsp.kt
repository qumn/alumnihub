package io.github.qumn.framework.web.common

import kotlinx.serialization.Serializable

const val SUCCESS_CODE: Int = 200

/**
 * a class for warping the real data to client
 */
@Serializable
data class Rsp<T>(
    val code: Int,
    val msg: String,
    val data: T?,
) {
    companion object {
        fun <T> success(data: T): Rsp<T> {
            return Rsp(SUCCESS_CODE, "", data)
        }

        fun success(): Rsp<Unit> {
            return Rsp(SUCCESS_CODE, "", null)
        }

        fun <T> err(code: Int = 400, msg: String = "未知错误,请联系开发者", data: T? = null): Rsp<T> {
            return Rsp(code, msg, data)
        }
    }
}

/**
 * wrapper the T, if the T is not null, using the success function
 * otherwise using the err function
 */
fun <T> T?.toRsp(): Rsp<T> {
    return this?.success() ?: Rsp.err()
}

fun <T> T.success(): Rsp<T> {
    return Rsp.success(this)
}

fun <T> T.err(code: Int = 400, msg: String = "未知错误,请联系开发者"): Rsp<T> {
    return Rsp.err(code, msg, this)
}