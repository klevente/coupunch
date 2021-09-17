package dev.klevente.coupunch.couponmanager.config

import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config")
class CompanyConfigController(
    private val companyConfigActions: CompanyConfigActions
) {
    @GetMapping("metabase/url")
    fun getMetabaseUrl() = ok(companyConfigActions.getMetabaseUrl())

    @GetMapping("metabase/iframe-url")
    fun getMetabaseIframeUrl() = ok(companyConfigActions.generateMetabaseIframeUrl())
}