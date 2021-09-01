import { writable } from 'svelte/store';

export function searchStore() {
    return writable('');
}

export function sortByStore(initialProperty = 'id') {
    const initialValue = {
        property: initialProperty,
        order: 'asc'
    };
    const { subscribe, set, update } = writable(initialValue);

    return {
        subscribe,
        setProperty: (newProperty) => set({
            property: newProperty,
            order: 'asc'
        }),
        toggleOrder: () => update(({ property, order }) => {
            return order === 'asc' ? { property , order: 'desc' } : initialValue;
        }),
        reset: () => set(initialValue)
    };
}

export function categoryStore(initial = null) {
    return writable(initial);
}
