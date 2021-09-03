import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';

export default class Viewmodel extends BaseViewmodel {
    coupon = dataStore();
    state = stateStore();

    async get(couponId) {
        await this.load({
            action: action(() => 'todo')
        });
    }

    get _resource() {
        return this.coupon;
    }

    get _state() {
        return this.state;
    }
}
