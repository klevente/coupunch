import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';
import { sortedSimple, mapped } from 'frontend-library/viewmodel/transformations';
import { get } from 'svelte/store';
/*import CouponService from '../../../services/mock/coupon-service';*/
import CouponService from '../../../services/coupon-service';
import UserService from '../../../services/user-service';
import CompanyService from '../../../services/company-service';

export default class Viewmodel extends BaseViewmodel {

    #session = null;
    #companyUrl = null;

    constructor(session, companyUrl) {
        super();
        this.#session = session;
        this.#companyUrl = companyUrl;
    }

    #actions = {
        getName: action(CompanyService.getCompanyName),
        getCoupons: action(CouponService.getCompanyCouponsForUser),
        generateQr: action(UserService.generateRedeemQr, 'Successfully generated QR code')
    }

    companyName = dataStore();
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

    async getAll() {
        await Promise.all([this.getName(), this.getCoupons()]);
    }

    async getName() {
        await this.load({
            dataStore: this.companyName,
            action: this.#actions.getName,
            serviceParams: this.#companyUrl
        });
    }

    async getCoupons() {
        await this.load({
            action: this.#actions.getCoupons,
            serviceParams: [this.#session, this.#companyUrl]
        });
    }

    redeemCoupon({ id }, reward) {
        this.#coupons.updateData(coupons =>
            coupons.map(coupon => coupon.id === id ? {
                ...coupon, reward
            } : coupon));
    }

    cancelRedeem({ id }) {
        this.#coupons.updateData(coupons =>
            coupons.map(({ reward, ...rest }) => rest.id === id ? rest : {
                ...rest, reward
            })
        );
    }

    resetRedeemStatus() {
        this.#coupons.updateData(coupons =>
            coupons.map(({ reward, ...rest }) => rest)
        );
    }

    async generateQr(successCallback) {
        const request = get(this.#coupons)
            .data
            .filter(({ reward }) => !!reward)
            .map(({ id, reward: { id: productId } }) => ({ id, productId }))

        await this.executeCustom({
            action: this.#actions.generateQr,
            serviceParams: { redeemedCoupons: request },
            successCallback
        });
    }

    get _resource() {
        return this.#coupons;
    }

    get _state() {
        return this.state;
    }
}
