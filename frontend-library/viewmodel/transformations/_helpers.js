import { isIterable } from '../../util/iterable';

export function throwIfNotIterable(dataStore) {
    if (!isIterable(dataStore.data)) {
        throw new TypeError('Supplied data store does not contain an iterable resource.');
    }
}

export function notSuccess(dataStore) {
    return !dataStore.success;
}

export function hasNoValidData(dataStore) {
    if (!dataStore.success) {
        return true;
    }
    if (isIterable(dataStore.data)) {
        return dataStore.data.length === 0;
    } else {
        return !dataStore.data || dataStore.data === {};
    }
}
