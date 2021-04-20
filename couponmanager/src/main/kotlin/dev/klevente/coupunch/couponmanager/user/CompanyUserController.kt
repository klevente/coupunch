package dev.klevente.coupunch.couponmanager.user

import org.springframework.http.ResponseEntity.ok
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class CompanyUserController(
    private val companyUserService: CompanyUserService
) {
    @GetMapping("current")
    @PreAuthorize("hasRole('COMPANY_USER')")
    fun getCurrentUser() = ok(companyUserService.getCurrentUserResponse())
}