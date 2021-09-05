import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';
import CouponService from '../../../../services/mock/coupon-service';

export default class Viewmodel extends BaseViewmodel {

    #session = null
    #companyUrl = null;
    #couponId = null;

    constructor(session, companyUrl, couponId) {
        super();
        this.#session = session;
        this.#companyUrl = companyUrl;
        this.#couponId = couponId;
    }

    coupon = dataStore();

    async get(couponId) {
        await this.load({
            action: action(CouponService.getCompanyCouponForUser),
            serviceParams: [this.#session, this.#companyUrl, couponId]
        });
    }

    get _resource() {
        return this.coupon;
    }
}
