import BaseViewmodel from '../../viewmodel/base-viewmodel';
import { debouncePromise } from '../../util/debounce';
import { throttlePromise } from '../../util/throttle';
import { action, dataStore, stateStore } from '../../viewmodel';
import { searchStore, sortByStore } from '../../viewmodel/transformations/stores';
import { filtered, sorted } from '../../viewmodel/transformations';
import CustomerService from '../../services/customer-service';

export default class Viewmodel extends BaseViewmodel {
    #customers = dataStore();
    state = stateStore();

    searchTerm = searchStore();
    sortBy = sortByStore();

    #filteredCustomers = filtered({
        dataStore: this.#customers,
        searchTerm: this.searchTerm,
        searchProperty: 'name'
    });
    displayedCustomers = sorted({
        dataStore: this.#filteredCustomers,
        sortBy: this.sortBy,
    });

    #foundCustomers = dataStore([]);
    foundCustomersSortBy = sortByStore();
    displayedFoundCustomers = sorted({
        dataStore: this.#foundCustomers,
        sortBy: this.foundCustomersSortBy
    });

    #actions = {
        get: action(CustomerService.get),
        searchCustomers: action(CustomerService.searchCustomers),
        addToCompany: action(CustomerService.addToCompany, 'Successfully added customer to the company'),
        validateQrCode: action(CustomerService.validateQrCode, 'Successfully parsed QR code')
    }

    async get() {
        await this.load({
            action: this.#actions.get
        });
    }

    async validateQrCode(qrContent, successCallback) {
        await this._throttledValidateQrCode(qrContent, successCallback);
    }

    async searchCustomers(keyword) {
        await this._debouncedSearchCustomers(keyword);
    }

    async addToCompany(customer, callback) {
        await this.executeCustom({
            stateStore: this.state,
            action: this.#actions.addToCompany,
            serviceParams: customer,
            successCallback: () => callback(customer)
        });
    }

    async _validateQrCode(qrContent, successCallback) {
        await this.executeCustom({
            stateStore: this.state,
            action: this.#actions.validateQrCode,
            serviceParams: qrContent,
            successCallback
        });
    }

    _throttledValidateQrCode = throttlePromise(this._validateQrCode.bind(this), 3000);

    async _searchCustomers(keyword) {
        await this.load({
            dataStore: this.#foundCustomers,
            action: this.#actions.searchCustomers,
            serviceParams: keyword
        });
    }

    _debouncedSearchCustomers = debouncePromise(this._searchCustomers.bind(this));

    get _resource() {
        return this.#customers;
    }

    get _state() {
        return this.state;
    }
}
