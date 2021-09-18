package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.couponmanager.config.dto.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
@Transactional(readOnly = true)
class CompanyConfigServiceImpl(
    private val log: Logger,
    private val configRepository: ConfigRepository,
    private val configEventPublisher: ConfigEventPublisher,
    @Value("\${spring.application.name}") private val companyId: String,
    @Value("\${company.url}") private val companyUrl: String,
    @Value("\${company.metabase.url}") private val metabaseUrl: String,
    @Value("\${company.metabase.key}") private val metabaseKey: String
) : CompanyConfigActions, CompanyConfigService {
    override fun getCompanyId() = companyId

    override fun getCompanyName() = getConfigValueAsString("name")

    override fun getCompanyUrl() = companyUrl

    override fun getCompanyCurrency() = getConfigValueAsString("currency")

    override fun getMetabaseKey() = metabaseKey

    override fun getCompanyNameResponse() = getCompanyName().toNameResponse()

    override fun getMetabaseUrlResponse(): UrlResponse = metabaseUrl.toUrlResponse()

    override fun generateMetabaseIframeUrlResponse(): UrlResponse {
        val key = Keys.hmacShaKeyFor(metabaseKey.toByteArray())

        val jws = Jwts.builder()
            .claim("resource", DashboardClaim(1))
            .claim("params", Unit)
            .setExpiration(Date.from(LocalDateTime
                .now()
                .plusHours(1)
                .atZone(ZoneId.systemDefault())
                .toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val url = "$metabaseUrl/embed/dashboard/$jws#bordered=true&titled=false"
        return url.toUrlResponse()
    }

    class DashboardClaim(
        val dashboard: Int
    )

    override fun getSettings() = getCompanyName().toSettingsResponse()

    @Transactional
    override fun updateSettings(request: SettingsUpdateRequest): SettingsResponse {
        val nameEntry = configRepository.findFirstByKey("name")!!
        nameEntry.value = request.name

        configEventPublisher.companyInformationUpdated()

        return nameEntry.value.toSettingsResponse()
    }

    override fun resendUpdateSettings() {
        // TODO remove this circular dependency somehow
        configEventPublisher.companyInformationUpdated()
    }

    private fun getConfigValueAsString(key: String) = configRepository
        .findFirstByKey(key)!!.string()
}