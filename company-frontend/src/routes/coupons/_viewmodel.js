import BaseViewmodel from '../../viewmodel/base-viewmodel';
import { action, dataStore, stateStore } from '../../viewmodel';
import NewCouponService from '../../services/new-coupon-service';

export default class Viewmodel extends BaseViewmodel {
    coupons = dataStore();
    state = stateStore();

    #actions = {
        get: action(NewCouponService.get),
        add: action(NewCouponService.add, 'Coupon added'),
        update: action(NewCouponService.update, 'Coupon updated'),
        delete: action(NewCouponService.delete, 'Coupon deleted')
    };

    constructor() {
        super(NewCouponService);
    }

    async get() {
        await this.load({
            action: this.#actions.get
        });
    }

    async add(coupon) {
        await this.execute({
            action: this.#actions.add,
            serviceParams: coupon,
            localCallback: this._defaultOperations().array.add
        });
    }

    async update(coupon) {
        await this.execute({
            action: this.#actions.update,
            serviceParams: coupon,
            localCallback: this._defaultOperations().array.update
        });
    }

    async delete(coupon) {
        await this.execute({
            action: this.#actions.delete,
            serviceParams: coupon,
            localCallback: this._defaultOperations().array.delete
        });
    }

    get _resource() {
        return this.coupons;
    }

    get _state() {
        return this.state;
    }
}
