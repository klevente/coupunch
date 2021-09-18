import FrontendService from './frontend-service';

export default class CompanyService extends FrontendService {
    static async getUserCompanies() {
        return await CompanyService.api
            .endpoint(`users/current/companies`)
            .get(({ companies }) => companies);
    }

    static async getCompanyName(companyUrl) {
        return await CompanyService.api
            .endpoint(`${companyUrl}/config/name`)
            .get(({ name }) => name);
    }
}
