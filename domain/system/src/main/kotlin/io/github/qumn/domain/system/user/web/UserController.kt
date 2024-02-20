package io.github.qumn.domain.system.user.web

import io.github.qumn.domain.system.user.command.RegisteredUserCommand
import io.github.qumn.domain.system.user.command.LoginCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.ktorm.database.Database

@RestController
@RequestMapping("/users")
class UserController(
    val commandGateway: CommandGateway
) {

    @PostMapping("/register")
    fun register(@RequestBody registerReq: RegisterReq) {
        val registeredUserCommand = RegisteredUserCommand(
            username = registerReq.username,
            password = registerReq.password,
            phoneNumber = registerReq.phoneNumber,
        )

        commandGateway.sendAndWait<Unit>(registeredUserCommand)
    }
    @PostMapping("/login")
    fun login(@RequestBody loginReq: LoginReq) {
        val loginCommand = LoginCommand(
            username = loginReq.username,
            password = loginReq.password,
        )

        commandGateway.sendAndWait<Unit>(loginCommand)
    }

    data class RegisterReq(
        val username: String,
        val password: String,
        val phoneNumber: String,
    )
    data class LoginReq(
        val username: String,
        val password: String,
    )
}
