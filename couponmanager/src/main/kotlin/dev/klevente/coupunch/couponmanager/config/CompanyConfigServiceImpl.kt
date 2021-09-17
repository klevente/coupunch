package dev.klevente.coupunch.couponmanager.config

import dev.klevente.coupunch.couponmanager.config.dto.UrlResponse
import dev.klevente.coupunch.couponmanager.config.dto.toUrlResponse
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
    @Value("\${spring.application.name}") private val companyId: String,
    @Value("\${company.metabase.url}") private val metabaseUrl: String,
    @Value("\${company.metabase.key}") private val metabaseKey: String
) : CompanyConfigActions, CompanyConfigService {
    override fun getCompanyId() = companyId

    override fun getCompanyName() = getConfigValueAsString("name")

    override fun getCompanyUrl() = getConfigValueAsString("url")

    override fun getCompanyCurrency() = getConfigValueAsString("currency")

    override fun getMetabaseKey() = metabaseKey

    override fun getMetabaseUrl(): UrlResponse = metabaseUrl.toUrlResponse()

    override fun generateMetabaseIframeUrl(): UrlResponse {
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

    private fun getConfigValueAsString(key: String) = configRepository
        .findFirstByKey(key)!!.string()
}