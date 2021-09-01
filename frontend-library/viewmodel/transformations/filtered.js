import { derived } from 'svelte/store';
import { hasNoValidData } from './_helpers';

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
