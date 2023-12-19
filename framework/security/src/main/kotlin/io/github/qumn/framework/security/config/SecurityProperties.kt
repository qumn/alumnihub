package io.github.qumn.framework.security.config

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration
import javax.crypto.SecretKey


@ConfigurationProperties("security")
class SecurityProperties(
    key: String,
    validity: Long,
) {

    private var _key: SecretKey = Keys.hmacShaKeyFor(key.toByteArray())
    private var _validity: Duration = Duration.ofSeconds(validity)

    fun key() =
        _key

    fun validity() =
        _validity
}