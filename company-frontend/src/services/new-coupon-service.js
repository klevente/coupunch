import sleep from '../util/sleep';
import CompanyUrlService from './companyurl-service';

let cnt = 3;

let dummyCoupons = [
    {
        id: 1,
        name: 'Free Coffee',
    },
    {
        id: 2,
        name: 'Discount Croissant',
    },
];

export default class NewCouponService extends CompanyUrlService {

    static async get() {
        await sleep();
        return [...dummyCoupons];
    }

    static async add(coupon, fetchCallback) {
        await sleep();
        const serverCoupon = {
            id: cnt++,
            ...coupon
        };
        dummyCoupons.push(serverCoupon);
        NewCouponService._cb(fetchCallback);
        return serverCoupon;
    }

    static async update(coupon, fetchCallback) {
        await sleep();
        const couponToUpdate = dummyCoupons.find(({ id }) => id === coupon.id);
        couponToUpdate.name = coupon.name;
        NewCouponService._cb(fetchCallback);
        return couponToUpdate;
    }

    static async delete(coupon, fetchCallback) {
        await sleep();
        const idToDelete = coupon.id;
        dummyCoupons = dummyCoupons.filter(({ id }) => id !== idToDelete);
        NewCouponService._cb(fetchCallback);
        return coupon;
    }

    static _cb(fetchCallback) {
        NewCouponService.get().then(fetchCallback);
    }
}
