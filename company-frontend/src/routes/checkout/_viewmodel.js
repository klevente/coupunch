import CompanyViewmodel from '../../viewmodel/company-viewmodel';
import { debouncePromise } from 'frontend-library/util/debounce';
import { throttlePromise } from 'frontend-library/util/throttle';
import { action, dataStore, stateStore } from 'frontend-library/viewmodel';
import { searchStore, sortByStore } from 'frontend-library/viewmodel/transformations/stores';
import { filtered, notIn, sorted } from 'frontend-library/viewmodel/transformations';
/*import CustomerService from '../../services/mock/customer-service';*/
import CustomerService from '../../services/customer-service';

export default class Viewmodel extends CompanyViewmodel {
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
    #foundCustomersWithoutCompanyCustomers = notIn({
        dataStore: this.#foundCustomers,
        otherStore: this.#customers,
        dataProperty: 'id'
    })
    foundCustomersSortBy = sortByStore();
    displayedFoundCustomers = sorted({
        dataStore: this.#foundCustomersWithoutCompanyCustomers,
        sortBy: this.foundCustomersSortBy
    });

    #actions = {
        get: action(CustomerService.get),
        searchCustomers: action(CustomerService.searchCustomers),
        addToCompany: action(CustomerService.addToCompany, 'Successfully added customer to the company'),
        validateQrCode: action(CustomerService.getCustomerByQrCode, 'Successfully parsed QR code'),
    };

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
        let couponsToRedeem = null;
        const index = qrContent.indexOf('?');
        if (index !== -1) {
            couponsToRedeem = qrContent.slice(index);
            qrContent = qrContent.slice(0, index);
        }
        await this.executeCustom({
            stateStore: this.state,
            action: this.#actions.validateQrCode,
            serviceParams: qrContent,
            successCallback: user => successCallback({ ...user, couponsToRedeem })
        });
    }

    _throttledValidateQrCode = throttlePromise(this._validateQrCode.bind(this), 3000);

    async _searchCustomers(keyword) {
        if (keyword.trim().length === 0) {
            this.#foundCustomers.setSuccess([]);
            return;
        }
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
