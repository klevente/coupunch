import { writable } from 'svelte/store';
import BaseViewmodel from '../../../viewmodel/base-viewmodel';
import { action, dataStore } from '../../../viewmodel';
import { searchStore, sortByStore } from '../../../viewmodel/transformations/stores';
import NewProductService from '../../../services/new-product-service';
import NewCouponService from '../../../services/new-coupon-service';
import { filteredAndSorted } from '../../../viewmodel/transformations';

export default class Viewmodel extends BaseViewmodel {

    #products = dataStore();
    #userCoupons = dataStore();

    basket = basket();

    productSearchTerm = searchStore();
    couponSearchTerm = searchStore();
    productSortBy = sortByStore();
    couponSortBy = sortByStore();

    displayedProducts = filteredAndSorted({
        dataStore: this.#products,
        searchTerm: this.productSearchTerm,
        sortBy: this.productSortBy
    });
    displayedCoupons = filteredAndSorted({
        dataStore: this.#userCoupons,
        searchTerm: this.couponSearchTerm,
        sortBy: this.couponSortBy
    });

    #actions = {
        getProducts: action(NewProductService.get),
        getCoupons: action(NewCouponService.getForUser)
    }

    constructor() {
        super(NewProductService, NewCouponService);
    }

    async getAll(username) {
        await Promise.all([this.getProducts(), this.getCoupons(username)]);
    }

    async getProducts() {
        await this.load({
            dataStore: this.#products,
            action: this.#actions.getProducts
        });
    }

    async getCoupons(username) {
        await this.load({
            dataStore: this.#userCoupons,
            action: this.#actions.getCoupons,
            serviceParams: username
        });
        this._addExtraCouponProperties()
    }

    addProductToBasket(product) {
        this.basket.addProduct(product);
    }

    toggleCouponRedeemStatus(coupon) {
        if (!coupon.redeemable) {
            return;
        }
        coupon.redeemed = !coupon.redeemed;
        this.basket.toggleRedeem(coupon);
    }

    removeProductFromBasket(product) {
        this.basket.removeItem(product);
    }

    removeRewardFromBasket(reward) {
        const { coupon } = reward;
        coupon.redeemed = false;
        this.basket.removeItem(reward);
    }

    _addExtraCouponProperties() {
        this.#userCoupons.updateData(coupons => coupons.map(coupon => ({
            ...coupon,
            redeemable: coupon.actual === coupon.required,
            redeemed: false
        })));
    }
}

function basket() {
    const { subscribe, set, update } = writable([]);

    return {
        subscribe,
        addProduct: (product) => {
            update(basket => {
                const existingProduct = basket.find(p => p.type === 'purchase' && p.id === product.id);
                if (existingProduct) {
                    existingProduct.amount++;
                } else {
                    const productToAdd = {
                        ...product,
                        amount: 1,
                        type: 'purchase'
                    };
                    basket.push(productToAdd);
                }
                return basket;
            });
        },
        toggleRedeem: (coupon) => {
            update(basket => {
                if (coupon.redeemed) {
                    const reward = {
                        ...coupon.reward,
                        amount: 1,
                        type: 'reward',
                        coupon
                    };
                    basket.push(reward);
                    return basket;
                } else {
                    return basket.filter(i => i.type === 'purchase' || i.coupon.id !== coupon.id);
                }
            });
        },
        removeItem: (item) => {
            update(basket => basket.filter(i => i !== item));
        },
        clear: () => set([])
    };
}
