import { create } from '@beyonk/sapper-httpclient';
import { company } from './companyurl';
import { isFunction } from '../util/function';

const api = create();

export default class ProductGroupService {
    static async get() {
        return await api
            .endpoint(company(`product-groups`))
            .get(json => json.productGroups)
    }

    static async add(productGroup, fetchCallback) {
        const newProductGroup = await api
            .endpoint(company(`product-groups`))
            .payload(productGroup)
            .post();
        ProductGroupService._cb(fetchCallback);
        return newProductGroup;
    }

    static async update(productGroup, fetchCallback) {
        const updatedProductGroup = await api
            .endpoint(company(`product-groups/${productGroup.id}`))
            .payload(productGroup)
            .put();
        ProductGroupService._cb(fetchCallback);
        return updatedProductGroup;
    }

    static async delete(productGroup, fetchCallback) {
        const deletedProductGroup = await api
            .endpoint(company(`product-groups/${productGroup.id}`))
            .payload(productGroup)
            .del();
        ProductGroupService._cb(fetchCallback);
        return deletedProductGroup;
    }

    static _cb(fetchCallback) {
        ProductGroupService.get().then(fetchCallback);
    }
}
