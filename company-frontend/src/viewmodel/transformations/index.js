import { derived, writable } from 'svelte/store';
import { isIterable } from '../../util/iterable';
import { resolve } from '../../util/resolve';

function throwIfNotIterable(dataStore) {
    if (!isIterable(dataStore.data)) {
        throw new TypeError('Supplied data store does not contain an iterable resource.');
    }
}

function hasNoData(dataStore) {
    return !dataStore.success;
}

export function filtered({
                             dataStore,
                             searchTerm,
                             searchProperty = 'name',
                         }) {
    return derived([dataStore, searchTerm], ([$dataStore, $searchTerm]) => {
        if (hasNoData($dataStore)) {
            return $dataStore;
        }
        throwIfNotIterable($dataStore);
        const dataStore = $dataStore._clone();
        const term = $searchTerm.toLowerCase().trim();
        const filteredData = dataStore.data.filter(element => {
            const elementValue = element[searchProperty].toLowerCase();
            return elementValue.includes(term);
        });
        dataStore._setSuccess(filteredData);
        return dataStore;
    });
}

const sortingStrategies = {
    string: {
        asc: (property) => (a, b) => a[property].localeCompare(b[property]),
        desc: (property) => (a, b) => b[property].localeCompare(a[property]),
    },
    number: {
        asc: (property) => (a, b) => a[property] - b[property],
        desc: (property) => (a, b) => b[property] - a[property],
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
        if (hasNoData($dataStore)) {
            return $dataStore;
        }
        throwIfNotIterable($dataStore);
        if ($dataStore.length === 0) {
            return $dataStore;
        }
        const dataStore = $dataStore._clone();
        const sortedArray = [...dataStore.data];
        const { property, order } = $sortBy;
        const type = typeof sortedArray[0][property];
        throwIfNotSupportedSortingStrategy(type, order);
        sortedArray.sort(sortingStrategies[type][order](property));
        dataStore._setSuccess(sortedArray);
        return dataStore;
    });
}

export function categorized({
                                dataStore,
                                selected,
                                dataProperty = 'path.to.field',
                                selectedProperty = 'path.to.field'
                            }) {
    return derived([dataStore, selected], ([$dataStore, $selected]) => {
        if (hasNoData($dataStore)) {
            return $dataStore;
        }
        throwIfNotIterable($dataStore);

        if (!$selected) {
            return $dataStore;
        }

        const dataStore = $dataStore._clone();
        const selectedValue = resolve(selectedProperty, $selected);
        const filteredData = dataStore.data.filter(element => {
            const elementValue = resolve(dataProperty, element);
            return selectedValue === elementValue;
        });
        dataStore._setSuccess(filteredData);
        return dataStore;
    });
}
