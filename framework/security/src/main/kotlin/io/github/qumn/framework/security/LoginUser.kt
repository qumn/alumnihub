package io.github.qumn.framework.security

import io.github.qumn.framework.security.config.SecurityProperties
import io.jsonwebtoken.Jwts
import java.util.*

data class LoginUser(
    val uid: Long,
    val username: String,
) {

    companion object {
        val threadLocal: ThreadLocal<LoginUser> = ThreadLocal()

        val current: LoginUser
            get() = threadLocal.get() ?: throw SecurityException("未登录")

        fun fromJwt(jwt: String, config: SecurityProperties): LoginUser? {
            val claims = Jwts.parser()
                .verifyWith(config.key())
                .build()
                .parseSignedClaims(jwt)
                .payload

            val uid = claims.subject.toLongOrNull() ?: return null
            val username = claims["una"] as? String ?: return null
            // verify expiration
            val expiration = claims.expiration ?: return null
            if (expiration.before(Date())) {
                return null
            }

            return LoginUser(uid, username)
        }
    }

    /**
     * save to thread local
     */
    fun save() {
        threadLocal.set(this)
    }

    /**
     * convert this to jwt string
     */
    fun toJwt(properties: SecurityProperties): String {
        val expiration = Date(System.currentTimeMillis() + properties.validity().toMillis())

        return Jwts.builder()
            .subject(uid.toString())
            .claim("una", username)
            .expiration(expiration)
            .signWith(properties.key())
            .compact()
    }
}