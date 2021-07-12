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
                             searchField = 'name',
                         }) {
    return derived([dataStore, searchTerm], ([$dataStore, $searchTerm]) => {
        if (hasNoData($dataStore)) {
            return $dataStore;
        }
        throwIfNotIterable($dataStore);
        const dataStore = $dataStore._clone();
        const term = $searchTerm.toLowerCase().trim();
        const filteredData = dataStore.data.filter(element => {
            const elementValue = element[searchField].toLowerCase();
            return elementValue.includes(term);
        });
        dataStore._setSuccess(filteredData);
        return dataStore;
    });
}

const sortingStrategies = {
    string: {
        asc: (sortBy) => (a, b) => a[sortBy].localeCompare(b[sortBy]),
        desc: (sortBy) => (a, b) => b[sortBy].localeCompare(a[sortBy]),
    },
    number: {
        asc: (sortBy) => (a, b) => a[sortBy] - b[sortBy],
        desc: (sortBy) => (a, b) => b[sortBy] - a[sortBy],
    }
};

function isSupportedSortingType(type) {
    return sortingStrategies.hasOwnProperty(type);
}

function isSupportedOrderByForType(type, orderBy) {
    return sortingStrategies[type].hasOwnProperty(orderBy);
}

function formatKeyList(obj) {
    return Object.keys(obj)
        .map(key => `'${key}'`)
        .join(', ');
}

function throwIfNotSupportedSortingStrategy(type, orderBy) {
    if (!isSupportedSortingType(type)) {
        throw new TypeError(`Unsupported sorted type: ${type}. Supported types: ${formatKeyList(sortingStrategies)}.`);
    }
    if (!isSupportedOrderByForType(type, orderBy)) {
        throw new Error(`Unsupported order by: ${orderBy}. Supported orderings: ${formatKeyList(sortingStrategies[type])}.`);
    }
}

export function sorted({
                           dataStore,
                           sortBy,
                           orderBy
                       }) {
    return derived([dataStore, sortBy, orderBy], ([$dataStore, $sortBy, $orderBy]) => {
        if (hasNoData($dataStore)) {
            return $dataStore;
        }
        throwIfNotIterable($dataStore);
        if ($dataStore.length === 0) {
            return $dataStore;
        }
        const dataStore = $dataStore._clone();
        const sortedArray = [...dataStore.data];
        const type = typeof sortedArray[0][$sortBy];
        throwIfNotSupportedSortingStrategy(type, $orderBy);
        sortedArray.sort(sortingStrategies[type][$orderBy]($sortBy));
        dataStore._setSuccess(sortedArray);
        return dataStore;
    });
}

export function categorized({
                                dataStore,
                                selected,
                                dataField = 'path.to.field',
                                selectedField = 'path.to.field'
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
        const selectedValue = resolve(selectedField, $selected);
        const filteredData = dataStore.data.filter(element => {
            const elementValue = resolve(dataField, element);
            return selectedValue === elementValue;
        });
        dataStore._setSuccess(filteredData);
        return dataStore;
    });
}

export function filteredAndSorted({
                                      dataStore,
                                      searchTerm,
                                      searchField = 'name',
                                      sortBy,
                                      orderBy
                                  }) {
    return sorted({
        dataStore: filtered({ dataStore, searchTerm, searchField }),
        sortBy, orderBy
    })
}
