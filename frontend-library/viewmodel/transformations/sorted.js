import { resolve } from '../../util/resolve';
import { derived } from 'svelte/store';
import { hasNoValidData } from './_helpers';

const sortingStrategies = {
    string: {
        asc: (property) => (a, b) => resolve(property, a).localeCompare(resolve(property, b)),
        desc: (property) => (a, b) => resolve(property, b).localeCompare(resolve(property, a)),
    },
    number: {
        asc: (property) => (a, b) => resolve(property, a) - resolve(property, b),
        desc: (property) => (a, b) => resolve(property, b) - resolve(property, a),
    }
};

function isSupportedSortingType(type) {
    return sortingStrategies.hasOwnProperty(type);
}

function isSupportedOrderByForType(type, order) {
    return sortingStrategies[type].hasOwnProperty(order);
}

function formatKeyList(obj) {
    return Object.keys(obj)
        .map(key => `'${key}'`)
        .join(', ');
}

function throwIfNotSupportedSortingStrategy(type, order) {
    if (!isSupportedSortingType(type)) {
        throw new TypeError(`Unsupported sorted type: ${type}. Supported types: ${formatKeyList(sortingStrategies)}.`);
    }
    if (!isSupportedOrderByForType(type, order)) {
        throw new Error(`Unsupported order by: ${order}. Supported orderings: ${formatKeyList(sortingStrategies[type])}.`);
    }
}

export function sorted({
                           dataStore,
                           sortBy
                       }) {
    return derived([dataStore, sortBy], ([$dataStore, $sortBy]) => {
        if (hasNoValidData($dataStore)) {
            return $dataStore;
        }

        const dataStore = $dataStore._clone();
        const sortedArray = [...dataStore.data];
        const { property, order } = $sortBy;
        const type = typeof resolve(property, sortedArray[0]);
        throwIfNotSupportedSortingStrategy(type, order);
        sortedArray.sort(sortingStrategies[type][order](property));
        dataStore._setSuccess(sortedArray);
        return dataStore;
    });
}
