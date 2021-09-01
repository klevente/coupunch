import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { updateCompanyUrl } from '../services/companyurl';

export default class CompanyViewmodel extends BaseViewmodel {
    constructor() {
        super();
        updateCompanyUrl();
    }
}
