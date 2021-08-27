import { create } from '@beyonk/sapper-httpclient';
import { company } from './companyurl';
import { isFunction } from '../util/function';

const api = create();

export default class CustomerService {
    static async get() {
        return await api
            .endpoint(company(`customers`))
            .get(json => json.customers);
    }

    static async searchCustomers(keyword) {
        return await api
            .endpoint(`users/search`)
            .query({ keyword })
            .get(json => json.results);
    }

    static async addToCompany(customer, fetchCallback) {
        const newCustomer = await api
            .endpoint(company(`customers`))
            .payload(customer)
            .post();
        CustomerService._cb(fetchCallback);
        return newCustomer;
    }

    static async getCustomerByQrCode(uuid) {
        // TODO: handle error when customer is not found
        let customer = await api
            .endpoint(company(`customers/${uuid}`))
            .get();

        let newlyAdded = false;
        if (!customer) {
            // TODO: handle error when customer is not found globally
            customer = await api
                .endpoint(`users/code/${uuid}`)
                .get();
            newlyAdded = true;
        }

        return { ...customer, newlyAdded };
    }

    static async getCustomerCoupons(username) {
        return await api
            .endpoint(company(`customers/${username}/coupons`))
            .get(json => json.coupons);
    }

    static _cb(fetchCallback) {
        if (!isFunction(fetchCallback)) {
            console.warn('No callback was supplied for fetch callback.');
            return;
        }
        CustomerService.get().then(fetchCallback);
    }
}
