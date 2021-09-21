package dev.klevente.coupunch.couponmanager.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ConfigValuesService(
    @Value("\${spring.application.name}") val companyId: String,
    @Value("\${company.url}") val companyUrl: String,
    @Value("\${company.metabase.url}") val metabaseUrl: String,
    @Value("\${company.metabase.key}") val metabaseKey: String
)