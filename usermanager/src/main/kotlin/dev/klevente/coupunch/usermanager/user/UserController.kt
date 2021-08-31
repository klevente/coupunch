package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.UserAddRequest
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest
import dev.klevente.coupunch.usermanager.user.qr.QrActions
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(
    private val userActions: UserActions,
    private val qrActions: QrActions
) {
    @PostMapping
    fun register(
        @RequestBody request: UserAddRequest
    ) = ok(userActions.register(request))

    @GetMapping("{id}")
    fun get(
        @PathVariable id: Long
    ) = ok(userActions.getUserResponse(id))

    @GetMapping("current")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser() = ok(userActions.getCurrentUserResponse())

    @GetMapping("search")
    fun searchUser(
        @RequestParam keyword: String
    ) = ok(userActions.searchUserByUsername(keyword))

    @PutMapping("{id}")
    fun updateUser(
        @PathVariable id: Long, request: UserUpdateRequest
    ) = ok(userActions.updateUser(id, request))

    @GetMapping("qr/{code}")
    fun getByCode(
        @PathVariable code: String
    ) = ok(userActions.getUserByCode(code))

    @GetMapping("{id}/qr", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getQr(
        @PathVariable id: Long
    ) = ok(qrActions.getQrCodeAsImage(id))

    @GetMapping("current/qr", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getQrForCurrentUser() = ok(qrActions.getQrCodeAsImageForCurrentUser())

    @GetMapping("{id}/qr/update", produces = [MediaType.IMAGE_PNG_VALUE])
    fun updateQr(
        @PathVariable id: Long
    ) = ok(qrActions.updateQrCodeFor(id))
}