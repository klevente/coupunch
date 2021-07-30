import sleep from '../util/sleep';
import CompanyUrlService from './companyurl-service';
import { generate } from '../util/array';

let dummyProducts = generate(20, i => ({
    id: i + 1,
    name: `Product ${i}`,
    price: Math.trunc((Math.random() * 50 + 1) * 100) / 100,
    group: Math.random() > 0.5 ? {
        id: 1,
        name: 'coffee'
    } : {
        id: 2,
        name: 'food'
    }
}));
let cnt = 21;

export default class ProductService extends CompanyUrlService {
    static async get() {
        await sleep();
        return [...dummyProducts];
    }

    static async add(product, fetchCallback) {
        await sleep();
        const serverProduct = {
            id: cnt++,
            ...product
        };
        dummyProducts.push(serverProduct);
        ProductService._cb(fetchCallback);
        return serverProduct;
    }

    static async update(product, fetchCallback) {
        await sleep();
        const idx = dummyProducts.findIndex(({ id }) => id === product.id);
        dummyProducts[idx] = product;
        ProductService._cb(fetchCallback);
        return product;
    }

    static async delete(product, fetchCallback) {
        await sleep();
        const idToDelete = product.id;
        dummyProducts = dummyProducts.filter(({ id }) => id !== idToDelete);
        ProductService._cb(fetchCallback);
        return product;
    }

    static _cb(fetchCallback) {
        ProductService.get().then(fetchCallback);
    }
}
