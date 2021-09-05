import { sleepRandom } from 'frontend-library/util/sleep';

let dummyUserCoupons =  [
    {
        id: 1,
        name: 'Free Coffee',
        type: 'point',
        progress: 12,
        redeemable: true,
        redeemLevel: 0,
        eligibleItems: {
            products: [
                {
                    id: 1,
                    name: 'Ham sandwich',
                    price: 5,
                    points: 1
                }
            ],
            productGroups: [
                {
                    id: 1,
                    name: 'coffee',
                    points: 2,
                    products: [
                        {
                            id: 10,
                            name: 'espresso',
                            price: 4
                        },
                        {
                            id: 11,
                            name: 'cappucino',
                            price: 5
                        }
                    ]
                }
            ]
        },
        rewards: [
            {
                threshold: 10,
                discountType: 'percentage',
                discount: 100,
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
                            },
                            {
                                id: 11,
                                name: 'cappucino',
                                originalPrice: 5,
                                discountedPrice: 0
                            }
                        ]
                    }
                ]
            },
            {
                threshold: 20,
                discountType: 'percentage',
                discount: 100,
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
                            },
                            {
                                id: 11,
                                name: 'cappucino',
                                originalPrice: 5,
                                discountedPrice: 0
                            }
                        ]
                    }
                ]
            }
        ]
    },
    {
        id: 2,
        name: 'Discount Sandwich or coffee for Croissants or cappuccino',
        type: 'price',
        progress: 85,
        redeemable: true,
        redeemLevel: 1,
        eligibleItems: {
            products: [
                {
                    id: 11,
                    name: 'cappucino',
                    price: 5,
                }
            ],
            productGroups: [
                {
                    id: 5,
                    name: 'croissant',
                    products: [
                        {
                            id: 20,
                            name: 'croissant',
                            price: 6
                        },
                        {
                            id: 21,
                            name: 'chocolate croissant',
                            price: 8
                        },
                    ]
                }
            ]
        },
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
                productGroups: [
                    {
                        id: 1,
                        name: 'coffee',
                        products: [
                            {
                                id: 10,
                                name: 'espresso',
                                originalPrice: 4,
                                discountedPrice: 1
                            },
                            {
                                id: 11,
                                name: 'cappucino',
                                originalPrice: 5,
                                discountedPrice: 2
                            }
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
                productGroups: [
                    {
                        id: 1,
                        name: 'coffee',
                        products: [
                            {
                                id: 10,
                                name: 'espresso',
                                originalPrice: 4,
                                discountedPrice: 1
                            },
                            {
                                id: 11,
                                name: 'cappucino',
                                originalPrice: 5,
                                discountedPrice: 3.75
                            }
                        ]
                    }
                ]
            }
        ]
    }
];

export default class CouponService {
    static async getCompanyCouponsForUser(session, companyUrl) {
        await sleepRandom();
        return [...dummyUserCoupons];
    }

    static async getCompanyCouponForUser(session, companyUrl, couponId) {
        await sleepRandom();
        couponId = parseInt(couponId);
        return dummyUserCoupons.find(({ id }) => id === couponId);
    }
}
