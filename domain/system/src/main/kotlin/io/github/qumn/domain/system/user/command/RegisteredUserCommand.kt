package io.github.qumn.domain.system.user.command

data class RegisteredUserCommand(
    val username: String,
    val password: String,
    val phoneNumber: String,
)
