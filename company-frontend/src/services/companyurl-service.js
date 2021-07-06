import { stores } from '@sapper/app';
import { get } from 'svelte/store';

export default class CompanyUrlService {
    static #companyUrl = null;

    static updateCompanyUrl() {
        const { session } = stores();
        const { user: { companyUrl } } = get(session);
        CompanyUrlService.#companyUrl = companyUrl;
    }

    static get companyUrl() {
        return CompanyUrlService.#companyUrl;
    }
}
