import { sleepRandom } from '../../util/sleep';
import { generate } from '../../util/array';
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
dummyProducts.push({
    id: 21,
    name: 'Product Without Group',
    price: 123,
    group: null
});
let cnt = 22;

export default class ProductService {
    static async get() {
        await sleepRandom();
        return [...dummyProducts];
    }

    static async add(product, fetchCallback) {
        await sleepRandom();
        const serverProduct = {
            ...product,
            id: cnt++,
            group: dummyGroups.find(({ id }) => id === product.group) || null
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
            group: dummyGroups.find(({ id }) => id === product.group) || null
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
