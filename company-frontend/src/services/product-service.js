import { create } from '@beyonk/sapper-httpclient';
import { logout } from '../util/logout';
import { derived, writable } from 'svelte/store';
import { Data } from './data';
import sleep from '../util/sleep';

export const defaultGroup = {
    id: 0,
    name: 'All'
}

class ProductService {
    products = writable(new Data());

    filteredProducts(selectedGroup, searchTerm = undefined) {
        if (!searchTerm) {
            return derived([this.products, selectedGroup], ([$products, $selectedGroup]) => {
                if ($selectedGroup === defaultGroup) {
                    $products.data.sort((a, b) => a.name.localeCompare(b.name));
                    return $products;
                }

                const ret = $products.clone();
                if ($products.hasData()) {
                    ret.data = $products.data.filter(p => p.group.id === $selectedGroup.id);
                }
                ret.data.sort((a, b) => a.name.localeCompare(b.name));
                return ret;
            });
        } else {
            return derived([this.products, selectedGroup, searchTerm], ([$products, $selectedGroup, $searchTerm]) => {
                if (!$products.hasData()) {
                    return $products;
                }
                let ret = $products.clone();
                if ($selectedGroup !== defaultGroup) {
                    ret.data = $products.data.filter(p => p.group.id === $selectedGroup.id);
                }

                const formattedTerm = $searchTerm.toLowerCase().trim();
                ret.data = ret.data.filter(p => {
                    const name = p.name.toLowerCase();
                    return name.includes(formattedTerm);
                });
                ret.data.sort((a, b) => a.name.localeCompare(b.name));
                return ret;
            });
        }
    }

    async fetch(companyUrl) {
        /*let api = create();
        const p = await api
            .endpoint(`${companyUrl}/products`)
            .accessDenied(e => {
                logout();
            })
            .default(e => throw e)
            .get(json => json.products)*/

        /*const p = [
            {
                id: 1,
                name: 'Product 1',
                price: 4.34,
                group: {
                    id: 1,
                    name: 'coffee'
                }
            },
            {
                id: 2,
                name: 'Product 2',
                price: 10.5,
                group: {
                    id: 2,
                    name: 'food'
                }
            }
        ];*/

        const p = [];
        for (let i = 1; i <= 20; i++) {
            p.push({
                id: i,
                name: `Product ${i}`,
                price: Math.trunc((Math.random() * 50 + 1) * 100) / 100,
                group: Math.random() > 0.5 ? {
                    id: 1,
                    name: 'coffee'
                } : {
                    id: 2,
                    name: 'food'
                }
            })
        }

        await sleep();
        this.products.update(products => products.setData(p));
    }

    async add(companyUrl, product) {
        const api = create();
        this.products.update(products => {
            products.data.push(product);
            return products;
        })
    }

    async update(companyUrl, product) {
        const api = create();
        this.products.update(products => {
            const index = products.data.findIndex(p => p.id === product.id);
            products.data[index] = product;
            return products;
        });
    }

    async delete(companyUrl, product) {
        const api = create();
        this.products.update(products => {
            products.data = products.data.filter(p => p !== product);
            return products;
        });
    }
}

export default new ProductService();
