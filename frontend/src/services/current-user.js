import { get } from 'svelte/store';
import { stores } from '@sapper/app';

export function userId() {
    const { session } = stores();
    const { user: { id } } = get(session);
    return id;
}
