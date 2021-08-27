import { create } from '@beyonk/sapper-httpclient';
import { company } from './companyurl';

const api = create();

export default class ProductService {
    static async get() {
        return await api
            .endpoint(company(`products`))
            .get(json => json.products);
    }

    static async add(product, fetchCallback) {
        const newProduct = await api
            .endpoint(company(`products`))
            .payload(product)
            .post();
        ProductService._cb(fetchCallback);
        return newProduct;
    }

    static async update(product, fetchCallback) {
        const updatedProduct = await api
            .endpoint(company(`products/${product.id}`))
            .payload(product)
            .put();
        ProductService._cb(fetchCallback);
        return updatedProduct;
    }

    static async delete(product, fetchCallback) {
        const deletedProduct = await api
            .endpoint(company(`products/${product.id}`))
            .del();
        ProductService._cb(fetchCallback);
        return deletedProduct;
    }

    static _cb(fetchCallback) {
        ProductService.get().then(fetchCallback);
    }
}
