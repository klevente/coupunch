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
        const idx = dummyCoupons.findIndex(({ id }) => id === coupon.id);
        dummyCoupons[idx] = coupon;
        NewCouponService._cb(fetchCallback);
        return coupon;
    }

    static async delete(coupon, fetchCallback) {
        await sleep();
        const idToDelete = coupon.id;
        dummyCoupons = dummyCoupons.filter(({ id }) => id !== idToDelete);
        NewCouponService._cb(fetchCallback);
        return coupon;
    }

    static async getForUser(username) {
        await sleep();
        return [
            {
                id: 1,
                name: 'Coupon 1',
                actual: 7,
                required: 10,
                products: [
                    {
                        id: 1,
                        name: 'Product 1'
                    }
                ],
                reward: {
                    id: 1,
                    name: 'Product 1'
                }
            },
            {
                id: 2,
                name: 'Coupon 2',
                actual: 8,
                required: 10,
                products: [
                    {
                        id: 2,
                        name: 'Product 2'
                    },
                    {
                        id: 3,
                        name: 'Product 3'
                    }
                ],
                reward: {
                    id: 2,
                    name: 'Product 2'
                }
            },
            {
                id: 3,
                name: 'Coupon 3',
                actual: 10,
                required: 10,
                products: [
                    {
                        id: 5,
                        name: 'Product 5'
                    }
                ],
                reward: {
                    id: 5,
                    name: 'Product 5'
                }
            }
        ];
    }

    static _cb(fetchCallback) {
        NewCouponService.get().then(fetchCallback);
    }
}
