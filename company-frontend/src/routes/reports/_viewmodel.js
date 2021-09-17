import CompanyViewmodel from '../../viewmodel/company-viewmodel';
import { action, dataStore } from 'frontend-library/viewmodel';
import ConfigService from '../../services/config-service';

export default class Viewmodel extends CompanyViewmodel {
    url = dataStore();
    iframeUrl = dataStore();

    async getAll() {
        await Promise.all([this.getUrl(), this.getIframeUrl()]);
    }

    async getUrl() {
        await this.load({
            dataStore: this.url,
            action: action(ConfigService.getMetabaseUrl)
        });
    }

    async getIframeUrl() {
        await this.load({
            dataStore: this.iframeUrl,
            action: action(ConfigService.getMetabaseIframeUrl)
        });
    }
}
