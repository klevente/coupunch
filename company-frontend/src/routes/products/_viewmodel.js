import BaseViewmodel from '../../viewmodel/base-viewmodel';
import { dataStore } from '../../viewmodel/data-store';
import { stateStore } from '../../viewmodel/state-store';
import { action } from '../../viewmodel/action';
import NewProductService from '../../services/new-product-service';
import NewProductGroupService from '../../services/new-product-group-service';
import { filtered, searchStore } from '../../viewmodel/transformations';

export default class Viewmodel extends BaseViewmodel {

    products = dataStore();
    productGroups = dataStore();
    state = stateStore();

    searchTerm = searchStore();
    filteredProducts = filtered({
        dataStore: this.products,
        searchTerm: this.searchTerm,
        searchField: 'name'
    });

    #actions = {
        getProducts: action(NewProductService.get),
        addProduct: action(NewProductGroupService.get, 'Product added'),
        updateProduct: action(NewProductService.update, 'Product updated'),
        deleteProduct: action(NewProductService.delete, 'Product deleted'),
        getProductGroups: action(NewProductGroupService.get),
        addProductGroup: action(NewProductGroupService.add, 'Product group added'),
        updateProductGroup: action(NewProductGroupService.update, 'Product group updated'),
        deleteProductGroup: action(NewProductGroupService.delete, 'Product group deleted')
    };

    constructor() {
        super(NewProductService, NewProductGroupService);
    }

    async getData() {
        await Promise.all([this.getProducts(), this.getProductGroups()]);
    }

    async getProducts() {
        await this.load({
            dataStore: this.products,
            action: this.#actions.getProducts
        });
    }

    async addProduct(product) {
        await this.execute({
            action: this.#actions.addProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.products),
            localCallback: this._defaultOperations(this.products).array.add
        });
    }

    async updateProduct(product) {
        await this.execute({
            action: this.#actions.updateProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.products),
            localCallback: this._defaultOperations(this.products).array.update
        });
    }

    async deleteProduct(product) {
        await this.execute({
            action: this.#actions.deleteProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.products),
            localCallback: this._defaultOperations(this.products).array.delete
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
