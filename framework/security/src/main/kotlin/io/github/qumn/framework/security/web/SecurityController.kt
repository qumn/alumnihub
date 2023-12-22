package io.github.qumn.framework.security.web

import io.github.qumn.framework.security.Authentication
import io.github.qumn.framework.security.LoginUser
import io.github.qumn.framework.security.config.SecurityProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/security")
class SecurityController(
    val securityProperties: SecurityProperties,
    val authentication: Authentication,
    val passwordEncoder: PasswordEncoder,
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): String {
        val encryptedPassword = passwordEncoder.encode(loginRequest.password)
        val loginUser = authentication.login(loginRequest.username, encryptedPassword)
        return loginUser.toJwt(securityProperties)
    }

    data class LoginRequest(
        val username: String,
        val password: String,
    )

    @GetMapping("/me")
    fun me(): LoginUser {
        return LoginUser.current()
    }
}