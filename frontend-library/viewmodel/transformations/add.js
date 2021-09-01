import { asArray } from '../../util/array';
import { derived } from 'svelte/store';
import { notSuccess, throwIfNotIterable } from './_helpers';

export function addFront({
                             dataStore,
                             items
                         }) {
    items = asArray(items);

    return derived(dataStore, $dataStore => {
        if (notSuccess($dataStore)) {
            return $dataStore;
        }
        throwIfNotIterable($dataStore);

        const dataStore = $dataStore._clone();
        dataStore._setSuccess([...items, ...dataStore.data]);
        return dataStore;
    });
}

export function addBack({
                            dataStore,
                            items
                        }) {
    items = asArray(items);

    return derived(dataStore, $dataStore => {
        if (notSuccess($dataStore)) {
            return $dataStore;
        }
        throwIfNotIterable($dataStore);

        const dataStore = $dataStore._clone();
        dataStore._setSuccess([...dataStore.data, ...items]);
        return dataStore;
    });
}
