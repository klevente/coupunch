import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, action, stateStore } from 'frontend-library/viewmodel';
import { sortByStore } from 'frontend-library/viewmodel/transformations/stores'
import { sorted } from 'frontend-library/viewmodel/transformations';
/*import CompanyService from '../../services/mock/company-service';*/
import CompanyService from '../../services/company-service';

export default class Viewmodel extends BaseViewmodel {
    #companies = dataStore();
    state = stateStore();

    sortBy = sortByStore('name');

    displayedCompanies = sorted({
        dataStore: this.#companies,
        sortBy: this.sortBy
    });

    async getUserCompanies() {
        await this.load({
           action: action(CompanyService.getUserCompanies)
        });
    }

    get _resource() {
        return this.#companies;
    }

    get _state() {
        return this.state;
    }
}
