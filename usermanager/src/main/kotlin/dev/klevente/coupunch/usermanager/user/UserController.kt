package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.NewUserRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    /*@PostMapping
    fun register(@RequestBody newUserRequest: NewUserRequest): ResponseEntity<Any> {
        userService.register(newUserRequest)
        return ResponseEntity.noContent().build()
    }*/
}