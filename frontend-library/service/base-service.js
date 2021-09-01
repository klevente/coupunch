import { create } from '@beyonk/sapper-httpclient';
import { isFunction } from '../util/function';

export default class BaseService {
    static #api = null;

    static get api() {
        if (!BaseService.#api) {
            BaseService.#api = create();
        }
        return BaseService.#api;
    }

    static _createCallback(methodToCall) {
        return (fetchCallback, ...args) => {
            if (!isFunction(fetchCallback)) {
                console.warn('No callback was supplied for fetch callback.');
                return;
            }
            methodToCall(...args);
        }
    }
}
