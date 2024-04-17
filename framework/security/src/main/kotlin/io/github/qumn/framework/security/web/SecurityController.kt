package io.github.qumn.framework.security.web

import io.github.qumn.framework.security.Authentication
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.security.config.SecurityProperties
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.common.toRsp
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/security")
class SecurityController(
    val securityProperties: SecurityProperties,
    val authentication: Authentication,
    val passwordEncoder: PasswordEncoder,
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Rsp<String> {
        val loginUser = authentication.login(loginRequest.username) { encrypted ->
            passwordEncoder.matches(loginRequest.password, encrypted)
        }
        return loginUser.toJwt(securityProperties).toRsp()
    }

    data class LoginRequest(
        val username: String,
        val password: String,
    )

    @GetMapping("/me")
    fun me(): LoginUser {
        return LoginUser.current
    }
}