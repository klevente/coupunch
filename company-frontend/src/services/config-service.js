import CompanyService from './company-service';
import { company } from './companyurl';

export default class ConfigService extends CompanyService {
    static async getMetabaseUrl() {
        return await ConfigService.api
            .endpoint(company(`config/metabase/url`))
            .get(({ url }) => url);
    }

    static async getMetabaseIframeUrl() {
        return await ConfigService.api
            .endpoint(company(`config/metabase/iframe-url`))
            .get(({ url }) => url);
    }
}
