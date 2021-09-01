import { company } from './companyurl';
import CompanyService from './company-service';

export default class ProductService extends CompanyService {
    static async get() {
        return await ProductService.api
            .endpoint(company(`products`))
            .get(({ products }) => products);
    }

    static async add(product, fetchCallback) {
        const newProduct = await ProductService.api
            .endpoint(company(`products`))
            .payload(product)
            .post();
        console.log(newProduct);
        ProductService._cb(fetchCallback);
        return newProduct;
    }

    static async update(product, fetchCallback) {
        const updatedProduct = await ProductService.api
            .endpoint(company(`products/${product.id}`))
            .payload(product)
            .put();
        ProductService._cb(fetchCallback);
        return updatedProduct;
    }

    static async delete(product, fetchCallback) {
        const deletedProduct = await ProductService.api
            .endpoint(company(`products/${product.id}`))
            .del();
        ProductService._cb(fetchCallback);
        return deletedProduct;
    }

    static _cb = ProductService._createCallback(ProductService.get);
}
