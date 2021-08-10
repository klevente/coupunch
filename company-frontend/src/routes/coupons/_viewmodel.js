import BaseViewmodel from '../../viewmodel/base-viewmodel';
import { action, dataStore, stateStore } from '../../viewmodel';
import CouponService from '../../services/coupon-service';
import { searchStore, sortByStore } from '../../viewmodel/transformations/stores';
import { filteredAndSorted } from '../../viewmodel/transformations';
import ProductService from '../../services/product-service';
import ProductGroupService from '../../services/product-group-service';

export default class Viewmodel extends BaseViewmodel {
    #coupons = dataStore();
    state = stateStore();

    sortBy = sortByStore();
    searchTerm = searchStore();

    displayedCoupons = filteredAndSorted({
        dataStore: this.#coupons,
        searchTerm: this.searchTerm,
        searchProperty: 'name',
        sortBy: this.sortBy
    });

    #products = dataStore();
    #productGroups = dataStore();

    productSearchTerm = searchStore();
    productGroupSearchTerm = searchStore();
    productSortBy = sortByStore();
    productGroupSortBy = sortByStore();

    displayedProducts = filteredAndSorted({
        dataStore: this.#products,
        searchTerm: this.productSearchTerm,
        searchProperty: 'name',
        sortBy: this.productSortBy
    });
    displayedProductGroups = filteredAndSorted({
        dataStore: this.#productGroups,
        searchTerm: this.productGroupSearchTerm,
        searchProperty: 'name',
        sortBy: this.productGroupSortBy
    });

    #actions = {
        get: action(CouponService.get),
        add: action(CouponService.add, 'Coupon added'),
        update: action(CouponService.update, 'Coupon updated'),
        delete: action(CouponService.delete, 'Coupon deleted'),
        getProducts: action(ProductService.get),
        getProductGroups: action(ProductGroupService.get)
    };

    async getAll() {
        await Promise.all([this.get(), this.getProducts(), this.getProductGroups()]);
    }

    async get() {
        await this.load({
            action: this.#actions.get
        });
    }

    async add(coupon) {
        await this.execute({
            action: this.#actions.add,
            serviceParams: coupon,
            localCallback: this._defaultOperations().array.add
        });
    }

    async update(coupon) {
        await this.execute({
            action: this.#actions.update,
            serviceParams: coupon,
            localCallback: this._defaultOperations().array.update
        });
    }

    async delete(coupon) {
        await this.execute({
            action: this.#actions.delete,
            serviceParams: coupon,
            localCallback: this._defaultOperations().array.delete
        });
    }

    async getProducts() {
        await this.load({
            dataStore: this.#products,
            action: this.#actions.getProducts
        });
    }

    async getProductGroups() {
        await this.load({
            dataStore: this.#productGroups,
            action: this.#actions.getProductGroups
        });
    }

    get _resource() {
        return this.#coupons;
    }

    get _state() {
        return this.state;
    }
}
