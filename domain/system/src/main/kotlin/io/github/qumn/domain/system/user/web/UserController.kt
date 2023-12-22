package io.github.qumn.domain.system.user.web

import io.github.qumn.domain.system.user.command.RegisteredUserCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    val commandGateway: CommandGateway
) {

    @PostMapping("/register")
    fun register(@RequestBody registerReq: RegisterReq) {
//        val registeredUser =  UserFactory.create(registerReq.username, registerReq.password, registerReq.phoneNumber)
        val registeredUserCommand = RegisteredUserCommand(
            username = registerReq.username,
            password = registerReq.password,
            phoneNumber = registerReq.phoneNumber,
        )

        commandGateway.sendAndWait<Unit>(registeredUserCommand)
    }

    data class RegisterReq(
        val username: String,
        val password: String,
        val phoneNumber: String,
    )

}
