import { company } from './companyurl';
import CompanyService from './company-service';

export default class CustomerService extends CompanyService {
    static async get() {
        return await CustomerService.api
            .endpoint(company(`customers`))
            .get(({ customers }) => customers);
    }

    static async searchCustomers(keyword) {
        return await CustomerService.api
            .endpoint(`users/search`)
            .query({ keyword })
            .get(json => json.results);
    }

    static async addToCompany(customer, fetchCallback) {
        const newCustomer = await CustomerService.api
            .endpoint(company(`customers`))
            .payload(customer)
            .post();
        CustomerService._cb(fetchCallback);
        return newCustomer;
    }

    static async getCustomerByQrCode(uuid) {
        // TODO: handle error when customer is not found
        let customer = await CustomerService.api
            .endpoint(company(`customers/qr/${uuid}`))
            .get();

        let newlyAdded = false;
        if (!customer) {
            // TODO: handle error when customer is not found globally
            customer = await CustomerService.api
                .endpoint(`users/qr/${uuid}`)
                .get();
            newlyAdded = true;
        }

        return { ...customer, newlyAdded };
    }

    static async getCustomerCoupons(username) {
        return await CustomerService.api
            .endpoint(company(`customers/${username}/coupons/checkout`))
            .get(({ coupons }) => coupons);
    }

    static async manuallyAddToUsersCompanies(username) {
        await CustomerService.api
            .endpoint(company(`customers/${username}/add-company`))
            .post();
    }

    static _cb = CustomerService._createCallback(CustomerService.get);
}
