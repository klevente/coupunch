package dev.klevente.coupunch.couponmanager.config

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CompanyConfigServiceImpl(
    private val log: Logger,
    @Value("\${company.name}") private val companyName: String,
    @Value("\${spring.application.name}") private val companyUrl: String,
    @Value("\${company.currency}") private val companyCurrency: String,
) : CompanyConfigService {

    override fun getCompanyName() = companyName

    override fun getCompanyUrl() = companyUrl

    override fun getCompanyCurrency() = companyCurrency
}