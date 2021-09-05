import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';
import { sortedSimple, mapped } from 'frontend-library/viewmodel/transformations';
import CouponService from '../../../services/mock/coupon-service';

export default class Viewmodel extends BaseViewmodel {

    #session = null;
    #companyUrl = null;

    constructor(session, companyUrl) {
        super();
        this.#session = session;
        this.#companyUrl = companyUrl;
    }

    #coupons = dataStore();
    state = stateStore();

    displayedCoupons = sortedSimple({
        dataStore: mapped({
            dataStore: this.#coupons,
            mapper: coupon => ({
                ...coupon,
                redeemLevelToDisplay: coupon.redeemLevel + 1,
            })
        }),
        sortProperty: 'name'
    });

    async get() {
        await this.load({
            action: action(CouponService.getCompanyCouponsForUser),
            serviceParams: [this.#session, this.#companyUrl]
        });
    }

    get _resource() {
        return this.#coupons;
    }

    get _state() {
        return this.state;
    }
}
