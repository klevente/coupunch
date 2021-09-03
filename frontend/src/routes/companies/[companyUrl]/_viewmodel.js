import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';

export default class Viewmodel extends BaseViewmodel {
    #coupons = dataStore();
    state = stateStore();

    get _resource() {
        return this.#coupons;
    }

    get _state() {
        return this.state;
    }
}
