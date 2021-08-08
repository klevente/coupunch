import { sleepRandom } from '../util/sleep';
import CompanyUrlService from './companyurl-service';
import { generate } from '../util/array';
import { dummyGroups } from './product-group-service';

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
        await sleepRandom();
        return [...dummyProducts];
    }

    static async add(product, fetchCallback) {
        await sleepRandom();
        const serverProduct = {
            ...product,
            id: cnt++,
            group: dummyGroups.find(({ id }) => id === product.group)
        };
        console.log(serverProduct);
        dummyProducts.push(serverProduct);
        ProductService._cb(fetchCallback);
        return serverProduct;
    }

    static async update(product, fetchCallback) {
        await sleepRandom();
        const idx = dummyProducts.findIndex(({ id }) => id === product.id);
        dummyProducts[idx] = {
            ...product,
            group: dummyGroups.find(({ id }) => id === product.group)
        };
        console.log(dummyProducts[idx]);
        ProductService._cb(fetchCallback);
        return product;
    }

    static async delete(product, fetchCallback) {
        await sleepRandom();
        const idToDelete = product.id;
        dummyProducts = dummyProducts.filter(({ id }) => id !== idToDelete);
        ProductService._cb(fetchCallback);
        return product;
    }

    static _cb(fetchCallback) {
        ProductService.get().then(fetchCallback);
    }
}
