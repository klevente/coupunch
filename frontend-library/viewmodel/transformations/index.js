import { sorted } from './sorted';
import { filtered } from './filtered';

export * from './add';
export * from './categorized';
export * from './filtered';
export * from './mapped';
export * from './not-in';
export * from './sorted';

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
