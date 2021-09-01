import { derived } from 'svelte/store';
import { isIterable } from '../../util/iterable';
import { hasNoValidData } from './_helpers';

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
