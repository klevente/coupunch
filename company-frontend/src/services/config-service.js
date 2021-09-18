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

    static async getSettings() {
        return await ConfigService.api
            .endpoint(company(`config/settings`))
            .get();
    }

    static async updateSettings() {
        return await ConfigService.api
            .endpoint(company(`config/settings`))
            .put();
    }

    static async resend() {
        return await ConfigService.api
            .endpoint(company(`config/settings/resned`))
            .post();
    }
}
