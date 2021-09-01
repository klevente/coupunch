import { derived } from 'svelte/store';
import { isIterable } from '../../util/iterable';
import { resolve } from '../../util/resolve';
import { asArray } from '../../util/array';

function throwIfNotIterable(dataStore) {
    if (!isIterable(dataStore.data)) {
        throw new TypeError('Supplied data store does not contain an iterable resource.');
    }
}

function notSuccess(dataStore) {
    return !dataStore.success;
}

function hasNoValidData(dataStore) {
    if (!dataStore.success) {
        return true;
    }
    if (isIterable(dataStore.data)) {
        return dataStore.data.length === 0;
    } else {
        return !dataStore.data || dataStore.data === {};
    }
}

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

export function mapped({
                           dataStore,
                           mapper
                       }) {
    return derived(dataStore, $dataStore => {
        if (hasNoValidData($dataStore)) {
            return $dataStore;
        }

        const dataStore = $dataStore._clone();
        const mappedData = isIterable(dataStore.data) ? dataStore.data.map(mapper) : mapper(dataStore.data);
        dataStore._setSuccess(mappedData);
        return dataStore;
    });
}

export function notIn({
                        dataStore,
                        otherStore,
                        dataProperty,
                        otherProperty = dataProperty
                    }) {
    return derived([dataStore, otherStore], ([$dataStore, $otherStore]) => {
        if (hasNoValidData($dataStore) || hasNoValidData($otherStore)) {
            return $dataStore;
        }

        const dataStore = $dataStore._clone();
        const filteredData = dataStore.data.filter(dataElement => {
            const dataValue = resolve(dataProperty, dataElement);
            return $otherStore.data.every(otherElement => resolve(otherProperty, otherElement) !== dataValue);
        });

        dataStore._setSuccess(filteredData);
        return dataStore;
    });
}

export function filtered({
                             dataStore,
                             searchTerm,
                             searchProperty = 'name',
                         }) {
    return derived([dataStore, searchTerm], ([$dataStore, $searchTerm]) => {
        if (hasNoValidData($dataStore)) {
            return $dataStore;
        }

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

export function categorized({
                                dataStore,
                                selected,
                                dataProperty = 'path.to.property',
                                selectedProperty = 'path.to.property',
                                selectAllValue = 'all'
                            }) {
    return derived([dataStore, selected], ([$dataStore, $selected]) => {
        if (hasNoValidData($dataStore)) {
            return $dataStore;
        }

        const selectedValue = resolve(selectedProperty, $selected);

        if (selectedValue === selectAllValue) {
            return $dataStore;
        }

        const dataStore = $dataStore._clone();
        const filteredData = dataStore.data.filter(element => {
            const elementValue = resolve(dataProperty, element);
            return selectedValue === elementValue;
        });
        dataStore._setSuccess(filteredData);
        return dataStore;
    });
}

export function filteredAndSorted({
                                      dataStore,
                                      searchTerm,
                                      searchProperty = 'name',
                                      sortBy
                                  }) {
    return sorted({
        dataStore: filtered({
            dataStore,
            searchTerm,
            searchProperty
        }),
        sortBy
    });
}
