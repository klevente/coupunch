import { sleepRandom } from '../util/sleep';
import CompanyUrlService from './companyurl-service';

let dummyGroups = [
    {
        id: 1,
        name: 'coffee',
    },
    {
        id: 2,
        name: 'food',
    }
];
let cnt = 3;

export default class ProductGroupService extends CompanyUrlService {

    static async get() {
        await sleepRandom();
        return [...dummyGroups];
    }

    static async add(productGroup, fetchCallback) {
        await sleepRandom();
        const serverProductGroup = {
            id: cnt++,
            ...productGroup
        };
        dummyGroups.push(serverProductGroup);
        ProductGroupService._cb(fetchCallback);
        return serverProductGroup;
    }

    static async update(productGroup, fetchCallback) {
        await sleepRandom();
        const idx = dummyGroups.findIndex(({ id }) => id === productGroup.id);
        dummyGroups[idx] = productGroup;
        ProductGroupService._cb(fetchCallback);
        return productGroup;
    }

    static async delete(productGroup, fetchCallback) {
        await sleepRandom();
        const idToDelete = productGroup.id;
        dummyGroups = dummyGroups.filter(({ id }) => id !== idToDelete);
        ProductGroupService._cb(fetchCallback);
        return productGroup;
    }

    static _cb(fetchCallback) {
        ProductGroupService.get().then(fetchCallback);
    }
}
