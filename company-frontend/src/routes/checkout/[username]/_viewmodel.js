import { writable, get } from 'svelte/store';
import CompanyViewmodel from '../../../viewmodel/company-viewmodel';
import { action, dataStore, stateStore } from 'frontend-library/viewmodel';
import { searchStore, sortByStore } from 'frontend-library/viewmodel/transformations/stores';
import { filteredAndSorted } from 'frontend-library/viewmodel/transformations';
import { asArray, isArray } from 'frontend-library/util/array';
/*import ProductService from '../../../services/mock/product-service';
import CouponService from '../../../services/mock/coupon-service';*/
import ProductService from '../../../services/product-service';
import CustomerService from '../../../services/customer-service';
import CheckoutService from '../../../services/checkout-service';

export default class Viewmodel extends CompanyViewmodel {

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
        getCoupons: action(CustomerService.getCustomerCoupons),
        checkout: action(CheckoutService.checkout, 'Successfully checked out items'),
    }

    async getAll(username) {
        await Promise.all([this.getProducts(), this.getCoupons(username)]);
    }

    initBasket(rewards) {
        if (!rewards) {
            return;
        }
        const couponsStore = get(this.#userCoupons);
        if (couponsStore.success) {
            const coupons = couponsStore.data;
            asArray(rewards)
                .map(elem => elem
                    .split(',')
                    .map(id => parseInt(id))
                )
                .forEach(([couponId, productId]) => {
                    const coupon = coupons.find(({ id }) => id === couponId);
                    if (!coupon) {
                        this.state.emitFailure("One of the requested coupons is not found.");
                        return;
                    }
                    if (!coupon.redeemable) {
                        this.state.emitFailure("One of the requested coupons is not redeemable.");
                    }
                    const reward = coupon.rewards[coupon.redeemLevel].products
                        .find(({ id }) => id === productId);
                    if (!reward) {
                        this.state.emitFailure("One of the requested rewards is not found.");
                        return;
                    }
                    this.redeemCoupon(coupon, reward);
                });
        }
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

    async checkout(username, callback) {
        const basket = get(this.basket);
        const basketRequest = basket.reduce(({ products, coupons }, item) => {
            const { type } = item;
            switch (type) {
                case 'purchase':
                    products.push({
                        id: item.id,
                        amount: item.amount
                    });
                    break;
                case 'reward':
                    coupons.push({
                        id: item.coupon.id,
                        productId: item.id,
                        amount: item.amount
                    });
                    break;
                default:
                    throw new Error(`Unknown basket item type: '${type}'`);
            }
            return { products, coupons };
        }, {
            products: [],
            coupons: []
        });
        await this.executeCustom({
            action: this.#actions.checkout,
            serviceParams: [username, basketRequest],
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
            redeemLevelToDisplay: coupon.redeemLevel + 1,
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
