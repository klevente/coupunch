package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.couponmanager.config.dto.SettingsUpdateRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/config")
class CompanyConfigController(
    private val companyConfigActions: CompanyConfigActions
) {
    @GetMapping("name")
    fun getName() = ok(companyConfigActions.getCompanyNameResponse())

    @GetMapping("metabase/url")
    fun getMetabaseUrl() = ok(companyConfigActions.getMetabaseUrlResponse())

    @GetMapping("metabase/iframe-url")
    fun getMetabaseIframeUrl() = ok(companyConfigActions.generateMetabaseIframeUrlResponse())

    @GetMapping("settings")
    fun getSettings() = ok(companyConfigActions.getSettings())

    @PutMapping("settings")
    fun updateSettings(
        @RequestBody request: SettingsUpdateRequest
    ) = ok(companyConfigActions.updateSettings(request))

    @PostMapping("settings/resend")
    fun resendUpdateSettings() = ok(companyConfigActions.resendUpdateSettings())
}