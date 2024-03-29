package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.UserAddRequest
import dev.klevente.coupunch.usermanager.user.dto.UserPasswordUpdateRequest
import dev.klevente.coupunch.usermanager.user.dto.UserUpdateRequest
import dev.klevente.coupunch.usermanager.user.qr.QrActions
import dev.klevente.coupunch.usermanager.user.qr.dto.QrRedeemRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("users")
class UserController(
    private val userActions: UserActions,
    private val qrActions: QrActions
) {
    @PostMapping
    fun register(
        @RequestBody @Valid request: UserAddRequest
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
        @PathVariable id: Long, @RequestBody @Valid request: UserUpdateRequest
    ) = ok(userActions.updateUser(id, request))

    @PutMapping("current")
    fun updateCurrentUser(
        @RequestBody @Valid request: UserUpdateRequest
    ) = ok(userActions.updateCurrentUser(request))

    @PutMapping("current/password")
    fun updateCurrentUserPassword(
        @RequestBody @Valid request: UserPasswordUpdateRequest
    ) = ok(userActions.updateCurrentUserPassword(request))

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

    @PutMapping("{id}/qr")
    fun updateQr(
        @PathVariable id: Long
    ) = ok(qrActions.updateQrCodeFor(id))

    @PutMapping("current/qr")
    fun updateQrForCurrentUser() = ok(qrActions.updateQrCodeForCurrentUser())

    @GetMapping("current/qr/export")
    fun exportQrForCurrentUser(response: HttpServletResponse): ResponseEntity<Any> {
        qrActions.exportQrForCurrentUser(response)
        return ok().build()
    }

    @PostMapping("current/qr/redeem", produces = [MediaType.IMAGE_PNG_VALUE])
    fun generateQrWithRedeemedCouponsForCurrentUser(
        @RequestBody @Valid request: QrRedeemRequest
    ) = ok(qrActions.getQrCodeWithRedeemedCouponsForCurrentUser(request))

    @GetMapping("current/companies")
    fun getCurrentUserCompanies() = ok(userActions.getCurrentUserCompanies())

    @PostMapping("current/resend")
    fun resendInfoToCurrentUsersCompanies() = ok(userActions.resendInfoToCurrentUsersCompanies())
}