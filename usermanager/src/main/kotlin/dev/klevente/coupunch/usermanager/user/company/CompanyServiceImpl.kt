package dev.klevente.coupunch.usermanager.user.company

import dev.klevente.coupunch.library.exception.EntityNotFoundException
import dev.klevente.coupunch.usermanager.user.company.dto.CompanyUpdateEvent
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CompanyServiceImpl(
    private val log: Logger,
    private val companyRepository: CompanyRepository
) : CompanyEvents, CompanyService {
    override fun getCompany(id: String) =
        companyRepository.findByIdOrNull(id) ?: throw EntityNotFoundException.byId(Company::class, id)

    @Transactional
    override fun updateOrAddCompany(event: CompanyUpdateEvent) {
        log.info("Processing company update event")
        val company = companyRepository.findByIdOrNull(event.id)
        if (company != null) {
            company.apply {
                name = event.name
                url = event.url
            }
            log.info("Successfully updated company $company")
        } else {
            val savedCompany = companyRepository.save(
                Company(
                    id = event.id,
                    name = event.name,
                    url = event.url
                )
            )
            log.info("Successfully added company $savedCompany")
        }
    }
}