import { company } from './companyurl';
import BaseService from './base-service';

export default class CouponService extends BaseService {
    static async get() {
        return await CouponService.api
            .endpoint(company(`coupons`))
            .get(({ coupons }) => coupons);
    }

    static async add(coupon, fetchCallback) {
        const newCoupon = await CouponService.api
            .endpoint(company(`coupons`))
            .payload(coupon)
            .post();
        CouponService._cb(fetchCallback);
        return newCoupon;
    }

    static async update(coupon, fetchCallback) {
        const updatedCoupon = await CouponService.api
            .endpoint(company(`coupons/${coupon.id}`))
            .payload(coupon)
            .put();
        CouponService._cb(fetchCallback);
        return updatedCoupon;
    }

    static async delete(coupon, fetchCallback) {
        const deleteCoupon = await CouponService.api
            .endpoint(company(`coupons/${coupon.id}`))
            .del();
        CouponService._cb(fetchCallback);
        return deleteCoupon;
    }

    static _cb = CouponService._createCallback(CouponService.get);
}
