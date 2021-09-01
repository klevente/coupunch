import BaseService from 'frontend-library/service/base-service';
import { create } from '@beyonk/sapper-httpclient';

export default class CompanyService extends BaseService {
    static #api = null;

    static get api() {
        if (!CompanyService.#api) {
            CompanyService.#api = create();
        }
        return CompanyService.#api;
    }
}
