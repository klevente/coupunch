import { create } from '@beyonk/sapper-httpclient';
import { logout } from '../util/logout';
import { writable } from 'svelte/store';
import { Data } from './data';
import sleep from '../util/sleep';

class ProductGroupService {

    productGroups = writable(new Data());

    async fetch(companyUrl) {
        this.productGroups.update(productGroups => productGroups.setLoading());
        /*const api = create();
        const productGroups = await api
            .endpoint(`${companyUrl}/product-groups`)
            .accessDenied(e => logout())
            .default(e => throw e)
            .get(json => json.productGroups)*/

        const groups = [
            {
                id: 1,
                name: 'coffee',
            },
            {
                id: 2,
                name: 'food',
            }
        ];

        await sleep();
        this.productGroups.update(productGroups => productGroups.setData(groups));
    }

    async add(companyUrl, productGroup) {
        const api = create();
    }

    async update(companyUrl, productGroup) {
        const api = create();
    }

    async delete(companyUrl, productGroup) {
        const api = create();
    }

}

export default new ProductGroupService();
