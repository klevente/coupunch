import { create } from '@beyonk/sapper-httpclient';
import { company } from './companyurl';
import { isFunction } from '../util/function';

const api = create();

export default class CouponService {
    static async get() {
        return await api
            .endpoint(company(`coupons`))
            .get(json => json.coupons);
    }

    static async add(coupon, fetchCallback) {
        const newCoupon = await api
            .endpoint(company(`coupons`))
            .payload(coupon)
            .post();
        CouponService._cb(fetchCallback);
        return newCoupon;
    }

    static async update(coupon, fetchCallback) {
        const updatedCoupon = await api
            .endpoint(company(`coupons/${coupon.id}`))
            .payload(coupon)
            .put();
        CouponService._cb(fetchCallback);
        return updatedCoupon;
    }

    static async delete(coupon, fetchCallback) {
        const deleteCoupon = await api
            .endpoint(company(`coupons/${coupon.id}`))
            .del();
        CouponService._cb(fetchCallback);
        return deleteCoupon;
    }

    static _cb(fetchCallback) {
        CouponService.get().then(fetchCallback);
    }
}
