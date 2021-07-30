import BaseViewmodel from '../../viewmodel/base-viewmodel';
import { action, dataStore, stateStore } from '../../viewmodel';
import ProductService from '../../services/product-service';
import ProductGroupService from '../../services/product-group-service';
import { categorized, filtered, sorted } from '../../viewmodel/transformations';
import { categoryStore, searchStore, sortByStore } from '../../viewmodel/transformations/stores';

export default class Viewmodel extends BaseViewmodel {

    #products = dataStore();
    productGroups = dataStore();
    state = stateStore();

    searchTerm = searchStore();
    selectedProductGroup = categoryStore();
    sortBy = sortByStore();
    #filteredProducts = filtered({
        dataStore: this.#products,
        searchTerm: this.searchTerm,
        searchProperty: 'name'
    });
    #categorizedProducts = categorized({
        dataStore: this.#filteredProducts,
        selected: this.selectedProductGroup,
        dataProperty: 'group.id',
        selectedProperty: 'id',
    });
    displayedProducts = sorted({
        dataStore: this.#categorizedProducts,
        sortBy: this.sortBy,
    });

    #actions = {
        getProducts: action(ProductService.get),
        addProduct: action(ProductService.add, 'Product added'),
        updateProduct: action(ProductService.update, 'Product updated'),
        deleteProduct: action(ProductService.delete, 'Product deleted'),
        getProductGroups: action(ProductGroupService.get),
        addProductGroup: action(ProductGroupService.add, 'Product group added'),
        updateProductGroup: action(ProductGroupService.update, 'Product group updated'),
        deleteProductGroup: action(ProductGroupService.delete, 'Product group deleted')
    };

    constructor() {
        super(ProductService, ProductGroupService);
    }

    async getAll() {
        await Promise.all([this.getProducts(), this.getProductGroups()]);
    }

    async getProducts() {
        await this.load({
            dataStore: this.#products,
            action: this.#actions.getProducts
        });
    }

    async addProduct(product) {
        await this.execute({
            action: this.#actions.addProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.#products),
            localCallback: this._defaultOperations(this.#products).array.add
        });
    }

    async updateProduct(product) {
        await this.execute({
            action: this.#actions.updateProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.#products),
            localCallback: this._defaultOperations(this.#products).array.update
        });
    }

    async deleteProduct(product) {
        await this.execute({
            action: this.#actions.deleteProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.#products),
            localCallback: this._defaultOperations(this.#products).array.delete
        });
    }

    async getProductGroups() {
        await this.load({
            dataStore: this.productGroups,
            action: this.#actions.getProductGroups
        });
    }

    async addProductGroup(productGroup) {
        await this.execute({
            action: this.#actions.addProductGroup,
            serviceParams: productGroup,
            serviceCallback: this._defaultServiceCallback(this.productGroups),
            localCallback: this._defaultOperations(this.productGroups).array.add
        });
    }

    async updateProductGroup(productGroup) {
        await this.execute({
            action: this.#actions.updateProductGroup,
            serviceParams: productGroup,
            serviceCallback: this._defaultServiceCallback(this.productGroups),
            localCallback: this._defaultOperations(this.productGroups).array.update
        });
    }

    async deleteProductGroup(productGroup) {
        await this.execute({
            action: this.#actions.deleteProductGroup,
            serviceParams: productGroup,
            serviceCallback: this._defaultServiceCallback(this.productGroups),
            localCallback: this._defaultOperations(this.productGroups).array.delete
        });
    }

    get _state() {
        return this.state;
    }
}
