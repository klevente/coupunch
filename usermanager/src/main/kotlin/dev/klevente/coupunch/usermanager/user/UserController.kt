package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.NewUserRequest
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val qrCodeService: QrCodeService
) {
    @PostMapping
    fun register(@RequestBody newUserRequest: NewUserRequest): ResponseEntity<Any> {
        userService.register(newUserRequest)
        return noContent().build()
    }

    @GetMapping("{id}")
    fun get(@PathVariable id: Long) = ok(userService.getUserResponse(id))

    @PutMapping("{id}")
    fun updateUser(@PathVariable id: Long, updateRequest: UserUpdateRequest): ResponseEntity<Any> {
        userService.updateUser(id, updateRequest)
        return noContent().build()
    }

    @GetMapping("{id}/qr", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getQr(@PathVariable id: Long) = ok(qrCodeService.getQrCodeAsImage(id))

    @GetMapping("{id}/qr/update")
    fun updateQr(@PathVariable id: Long): ResponseEntity<Any> {
        qrCodeService.generateQrCodeFor(id)
        return noContent().build()
    }
}