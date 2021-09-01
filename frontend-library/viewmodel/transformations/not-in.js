import { derived } from 'svelte/store';
import { resolve } from '../../util/resolve';
import { hasNoValidData } from './_helpers';

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
