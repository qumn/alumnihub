package io.github.qumn.domain.system.user.command

data class LoginCommand(
    val username: String,
    val password: String,
)
