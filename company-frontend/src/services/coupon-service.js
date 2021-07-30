import sleep from '../util/sleep';
import CompanyUrlService from './companyurl-service';

let cnt = 3;

let dummyCoupons = [
    {
        id: 1,
        name: 'Free Coffee',
        type: 'point',
        eligibleItems: {
            products: [
                {
                    id: 1,
                    name: 'Ham sandwich',
                    price: 5,
                    points: 1
                },
            ],
            productGroups: [
                {
                    id: 1,
                    name: 'coffee',
                    points: 2
                }
            ]
        },
        rewards: [
            {
                threshold: 10,
                products: [],
                productGroups: [
                    {
                        id: 1,
                        name: 'coffee',
                        amount: 1,
                        products: [
                            {
                                id: 10,
                                name: 'Espresso',
                                originalPrice: 4,
                                discountedPrice: 0
                            }
                        ]
                    }
                ],
                discountType: 'percentage',
                discount: 100
            },
            {
                threshold: 20,
                products: [],
                productGroups: [
                    {
                        id: 1,
                        name: 'coffee',
                        amount: 2,
                        products: [
                            {
                                id: 10,
                                name: 'Espresso',
                                originalPrice: 4,
                                discountedPrice: 0
                            }
                        ]
                    }
                ],
                discountType: 'percentage',
                discount: 100
            }
        ]
    },
    {
        id: 2,
        name: 'Discount Sandwich for Croissants',
        type: 'price',
        eligibleItems: {
            products: [
                {
                    id: 2,
                    name: 'Croissant',
                    price: 5
                },
                {
                    id: 3,
                    name: 'Chocolate Croissant',
                    price: 6
                }
            ],
            productGroups: []
        },
        rewards: [
            {
                threshold: 40,
                discountType: 'fixed',
                discount: 3,
                products: [],
                productGroups: [
                    {
                        id: 2,
                        name: 'sandwich',
                        amount: 1,
                        products: [
                            {
                                id: 1,
                                name: 'Ham Sandwich',
                                originalPrice: 5,
                                discountedPrice: 2
                            },
                            {
                                id: 4,
                                name: 'Salami Sandwich',
                                originalPrice: 10,
                                discountedPrice: 7
                            },
                        ]
                    }
                ]
            },
            {
                threshold: 80,
                discountType: 'percentage',
                discount: 75,
                products: [
                    {
                        id: 5,
                        name: 'Premium Sandwich',
                        amount: 2,
                        originalPrice: 20,
                        discountedPrice: 5
                    }
                ],
                productGroups: []
            }
        ]
    },
];

let dummyUserCoupons =  [
    {
        id: 1,
        name: 'Free Coffee',
        type: 'point',
        currentStanding: 12,
        redeemable: true,
        redeemLevel: 0,
        rewards: [
            {
                threshold: 10,
                discountType: 'percentage',
                discount: 100,
                products: [
                    {
                        id: 10,
                        name: 'Espresso',
                        amount: 1,
                        originalPrice: 4,
                        discountedPrice: 0
                    }
                ],
            },
            {
                threshold: 20,
                discountType: 'percentage',
                discount: 100,
                products: [
                    {
                        id: 10,
                        name: 'Espresso',
                        amount: 2,
                        originalPrice: 4,
                        discountedPrice: 0
                    }
                ]
            }
        ]
    },
    {
        id: 2,
        name: 'Discount Sandwich for Croissants',
        type: 'price',
        currentStanding: 85,
        redeemable: true,
        redeemLevel: 1,
        rewards: [
            {
                threshold: 40,
                discountType: 'fixed',
                discount: 3,
                products: [
                    {
                        id: 1,
                        name: 'Ham Sandwich',
                        amount: 1,
                        originalPrice: 5,
                        discountedPrice: 2
                    },
                    {
                        id: 4,
                        name: 'Salami Sandwich',
                        amount: 1,
                        originalPrice: 10,
                        discountedPrice: 7
                    },
                ],
            },
            {
                threshold: 80,
                discountType: 'percentage',
                discount: 75,
                products: [
                    {
                        id: 5,
                        name: 'Premium Sandwich',
                        amount: 2,
                        originalPrice: 20,
                        discountedPrice: 5
                    }
                ]
            }
        ]
    }
];

export default class CouponService extends CompanyUrlService {

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
        CouponService._cb(fetchCallback);
        return serverCoupon;
    }

    static async update(coupon, fetchCallback) {
        await sleep();
        const idx = dummyCoupons.findIndex(({ id }) => id === coupon.id);
        dummyCoupons[idx] = coupon;
        CouponService._cb(fetchCallback);
        return coupon;
    }

    static async delete(coupon, fetchCallback) {
        await sleep();
        const idToDelete = coupon.id;
        dummyCoupons = dummyCoupons.filter(({ id }) => id !== idToDelete);
        CouponService._cb(fetchCallback);
        return coupon;
    }

    static async getForUser(username) {
        await sleep();
        return [...dummyUserCoupons];
    }

    static async checkout(basket) {
        await sleep();
        console.log(basket);
    }

    static _cb(fetchCallback) {
        CouponService.get().then(fetchCallback);
    }
}
