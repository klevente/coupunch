import CompanyViewmodel from '../../viewmodel/company-viewmodel';
import { action, dataStore, stateStore } from 'frontend-library/viewmodel';
/*import ProductService from '../../services/mock/product-service';
import ProductGroupService from '../../services/mock/product-group-service';*/
import ProductService from '../../services/product-service';
import ProductGroupService from '../../services/product-group-service';
import { categorized, filtered, sorted, sortedSimple, mapped, addFront } from 'frontend-library/viewmodel/transformations';
import { categoryStore, searchStore, sortByStore } from 'frontend-library/viewmodel/transformations/stores';

export default class Viewmodel extends CompanyViewmodel {

    #products = dataStore();
    #productGroups = dataStore();
    state = stateStore();

    searchTerm = searchStore();
    selectedProductGroup = categoryStore({ id: 'all' });
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
        selectAllValue: 'all'
    });
    displayedProducts = sorted({
        dataStore: this.#categorizedProducts,
        sortBy: this.sortBy,
    });
    displayedProductGroups = sortedSimple({
        dataStore: this.#productGroups,
        sortProperty: 'name'
    });
    productGroupChips = addFront({
        dataStore: mapped({
            dataStore: this.#productGroups,
            mapper: ({ id: value, name: label }) => ({ value, label })
        }),
        items: { value: 'uncategorized', label: 'Uncategorized' }
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

    async getAll() {
        await Promise.all([this.getProducts(), this.getProductGroups()]);
    }

    async getProducts() {
        await this.load({
            dataStore: this.#products,
            action: this.#actions.getProducts
        });
    }

    async addProduct(product, successCallback) {
        await this.execute({
            action: this.#actions.addProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.#products),
            localCallback: this._defaultOperations(this.#products).array.add,
            successCallback
        });
    }

    async updateProduct(product, successCallback) {
        await this.execute({
            action: this.#actions.updateProduct,
            serviceParams: product,
            serviceCallback: this._defaultServiceCallback(this.#products),
            localCallback: this._defaultOperations(this.#products).array.update,
            successCallback
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
            dataStore: this.#productGroups,
            action: this.#actions.getProductGroups
        });
    }

    async addProductGroup(productGroup, successCallback) {
        await this.execute({
            action: this.#actions.addProductGroup,
            serviceParams: productGroup,
            serviceCallback: this._defaultServiceCallback(this.#productGroups),
            localCallback: this._defaultOperations(this.#productGroups).array.add,
            successCallback
        });
    }

    async updateProductGroup(productGroup, successCallback) {
        await this.execute({
            action: this.#actions.updateProductGroup,
            serviceParams: productGroup,
            serviceCallback: this._defaultServiceCallback(this.#productGroups),
            localCallback: this._defaultOperations(this.#productGroups).array.update,
            successCallback
        });
    }

    async deleteProductGroup(productGroup) {
        await this.execute({
            action: this.#actions.deleteProductGroup,
            serviceParams: productGroup,
            serviceCallback: this._defaultServiceCallback(this.#productGroups),
            localCallback: this._defaultOperations(this.#productGroups).array.delete
        });
    }

    get _state() {
        return this.state;
    }
}
