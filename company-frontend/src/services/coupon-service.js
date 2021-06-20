import { writable } from "svelte/store";
import { Data } from "./data";

class CouponService {

    coupons = writable(new Data());
    couponsForUser = writable(new Data());

    async fetch(companyUrl) {

    }

    async fetchForUser(companyUrl, username) {
        this.couponsForUser.update(couponsForUser => couponsForUser.setLoading());

        const coupons = [
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

        this.couponsForUser.update(couponsForUser => couponsForUser.setData(coupons));
    }
}

export default new CouponService();
