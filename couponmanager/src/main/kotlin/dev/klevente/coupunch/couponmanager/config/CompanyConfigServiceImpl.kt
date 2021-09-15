package dev.klevente.coupunch.couponmanager.config

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CompanyConfigServiceImpl(
    private val log: Logger,
    @Value("\${spring.application.name}") private val companyId: String,
    @Value("\${company.name}") private val companyName: String,
    @Value("\${company.url}") private val companyUrl: String,
    @Value("\${company.currency}") private val companyCurrency: String,
) : CompanyConfigService {
    override fun getCompanyId() = companyId

    override fun getCompanyName() = companyName

    override fun getCompanyUrl() = companyUrl

    override fun getCompanyCurrency() = companyCurrency
}