package io.github.qumn.framework.storage.model

import com.fasterxml.jackson.annotation.JsonValue
import io.github.qumn.framework.exception.BizNotAllowedException

class URN(@get:JsonValue val name: String) {

    companion object {
        fun generate(ext: String): URN {
            return URN("alumnihub_${Thread.currentThread().id}_${System.currentTimeMillis()}.${ext}")
        }
    }

    fun toURL(): URL {
        return URL(this)
    }

    override fun toString(): String {
        return name
    }
}

class URL(
    private val urn: URN,
    private val domain: String = "sc1i8ek9f.hd-bkt.clouddn.com",
    private val protocol: String = "http",
) {
    init {
        if (!domain.contains(".")) {
            throw BizNotAllowedException("域名不合法")
        }
    }

    val location: String
        get() = "${protocol}://${domain}/${urn}"
}