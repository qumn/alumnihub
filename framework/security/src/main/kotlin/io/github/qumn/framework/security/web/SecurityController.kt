package io.github.qumn.framework.security.web

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController(
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Boolean {
        // TODO: using jwt to complete the login function
        return true
    }

    data class LoginRequest(
        val username: String,
        val password: String,
    )
}