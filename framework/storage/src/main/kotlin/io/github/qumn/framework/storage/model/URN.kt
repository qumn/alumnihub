package io.github.qumn.framework.storage.model

import io.github.qumn.framework.exception.BizNotAllowedException

@JvmInline
value class URN(val name: String) {
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
    val urn: URN,
    val domain: String = "sc1i8ek9f.hd-bkt.clouddn.com",
    val protocol: String = "http",
) {
    init {
        if (!domain.contains(".")) {
            throw BizNotAllowedException("域名不合法")
        }
    }

    val location: String
        get() = "${protocol}://${domain}/${urn}"
}