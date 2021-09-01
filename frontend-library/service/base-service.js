import { isFunction } from '../util/function';

export default class BaseService {
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
