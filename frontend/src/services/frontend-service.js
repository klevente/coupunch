import { create } from '@beyonk/sapper-httpclient';
import BaseService from 'frontend-library/service/base-service';

export default class FrontendService extends BaseService {
    static #api = null;

    static get api() {
        if (!FrontendService.#api) {
            FrontendService.#api = create();
        }
        return FrontendService.#api;
    }
}
