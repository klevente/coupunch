import { company } from './companyurl';
import CompanyService from './company-service';

export default class ProductGroupService extends CompanyService {
    static async get() {
        return await ProductGroupService.api
            .endpoint(company(`product-groups`))
            .get(({ productGroups }) => productGroups)
    }

    static async add(productGroup, fetchCallback) {
        const newProductGroup = await ProductGroupService.api
            .endpoint(company(`product-groups`))
            .payload(productGroup)
            .post();
        ProductGroupService._cb(fetchCallback);
        return newProductGroup;
    }

    static async update(productGroup, fetchCallback) {
        const updatedProductGroup = await ProductGroupService.api
            .endpoint(company(`product-groups/${productGroup.id}`))
            .payload(productGroup)
            .put();
        ProductGroupService._cb(fetchCallback);
        return updatedProductGroup;
    }

    static async delete(productGroup, fetchCallback) {
        const deletedProductGroup = await ProductGroupService.api
            .endpoint(company(`product-groups/${productGroup.id}`))
            .payload(productGroup)
            .del();
        ProductGroupService._cb(fetchCallback);
        return deletedProductGroup;
    }

    /*static _cb(fetchCallback) {
        ProductGroupService.get().then(fetchCallback);
    }*/

    static _cb = ProductGroupService._createCallback(ProductGroupService.get);
}
