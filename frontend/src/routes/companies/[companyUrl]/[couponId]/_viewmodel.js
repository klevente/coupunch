import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';
import { mapped, sorted } from 'frontend-library/viewmodel/transformations';
import { sortByStore } from 'frontend-library/viewmodel/transformations/stores';
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

    displayedCoupon = mapped({
        dataStore: this.coupon,
        mapper: coupon => ({
            ...coupon,
            eligibleItems: [
                ...coupon.eligibleItems.products.map(p => ({ ...p, type: 'product' })),
                ...coupon.eligibleItems.productGroups.map(g => ({ ...g, type: 'group' }))
            ],
            rewards: coupon.rewards.map(({
                                             threshold, discountType, discount, products, productGroups
                                         }) => ({
                threshold, discountType, discount,
                items: [
                    ...products.map(p => ({ ...p, type: 'product' })),
                    ...productGroups.map(g => ({ ...g, type: 'group' }))
                ]
            }))
        })
    });

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
