import sleep from '../util/sleep';
import CompanyUrlService from './companyurl-service';

let dummyProducts = new Array(20).map((_, i) => ({
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

export default class NewProductService extends CompanyUrlService {
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
        NewProductService._cb(fetchCallback);
        return serverProduct;
    }

    static async update(product, fetchCallback) {
        await sleep();
        const idx = dummyProducts.findIndex(({ id }) => id === product.id);
        dummyProducts[idx] = product;
        NewProductService._cb(fetchCallback);
        return product;
    }

    static async delete(product, fetchCallback) {
        await sleep();
        const idToDelete = product.id;
        dummyProducts = dummyProducts.filter(({ id }) => id !== idToDelete);
        NewProductService._cb(fetchCallback);
        return product;
    }

    static _cb(fetchCallback) {
        NewProductService.get().then(fetchCallback);
    }
}
