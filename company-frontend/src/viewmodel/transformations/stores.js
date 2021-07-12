import { writable } from 'svelte/store';

export function searchStore() {
    return writable('');
}

export function sortByStore(initial) {
    return writable(initial);
}

export function orderByStore(order = 'asc') {
    return writable(order);
}

export function categoryStore(initial = null) {
    return writable(initial);
}
