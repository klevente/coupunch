import { derived } from 'svelte/store';
import { resolve } from '../../util/resolve';
import { hasNoValidData } from './_helpers';

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
