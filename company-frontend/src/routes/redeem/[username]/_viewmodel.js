import { writable, get } from 'svelte/store';
import BaseViewmodel from '../../../viewmodel/base-viewmodel';
import { action, dataStore, stateStore } from '../../../viewmodel';
import { searchStore, sortByStore } from '../../../viewmodel/transformations/stores';
import ProductService from '../../../services/product-service';
import CouponService from '../../../services/coupon-service';
import { filteredAndSorted } from '../../../viewmodel/transformations';

export default class Viewmodel extends BaseViewmodel {

    state = stateStore();

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
        getProducts: action(ProductService.get),
        getCoupons: action(CouponService.getForUser),
        checkout: action(CouponService.checkout, 'Successfully checked out items'),
    }

    constructor() {
        super(ProductService, CouponService);
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

    async checkout(callback) {
        const basket = get(this.basket);
        const basketRequest = basket.reduce(({ products, coupons }, item) => {
            const { type } = item;
            if (type === 'purchase') {
                products.push({
                    id: item.id,
                    amount: item.amount
                });
            } else if (type === 'reward') {
                coupons.push({
                    id: item.coupon.id,
                    productId: item.id,
                    amount: item.amount
                });
            } else {
                throw new Error(`Unknown basket item type: '${type}'`);
            }
            return { products, coupons };
        }, {
            products: [],
            coupons: []
        });
        await this.executeCustom({
            action: this.#actions.checkout,
            serviceParams: basketRequest,
            successCallback: () => {
                const discountedProducts = basket
                    .filter(({ type }) => type === 'reward')
                    .map(({ name, amount, originalPrice, discountedPrice, coupon }) => ({
                        name: name,
                        amount: amount,
                        originalPrice: originalPrice,
                        discountedPrice: discountedPrice,
                        couponName: coupon.name
                    }));
                callback(discountedProducts);
            }
        });
    }

    addProductToBasket(product) {
        this.basket.addProduct(product);
    }

    redeemCoupon(coupon, chosenReward) {
        if (!coupon.redeemable || coupon.redeemed) {
            return;
        }
        coupon.redeemed = true;
        const reward = {
            ...chosenReward,
            coupon
        };
        this.basket.addReward(reward);
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
            redeemed: false
        })));
    }

    get _state() {
        return this.state;
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
        addReward: (reward) => {
            update(basket => {
                const rewardToAdd = {
                    ...reward,
                    type: 'reward'
                };
                basket.push(rewardToAdd);
                return basket;

            });
        },
        removeItem: (item) => {
            update(basket => basket.filter(i => i !== item));
        },
        clear: () => set([])
    };
}
