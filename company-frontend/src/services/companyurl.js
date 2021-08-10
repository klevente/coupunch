import { stores } from '@sapper/app';
import { get } from 'svelte/store';

let _companyUrl = null;

export function updateCompanyUrl() {
    const { session } = stores();
    const { user: { companyUrl: url } } = get(session);
    _companyUrl = url;
}

export function companyUrl() {
    return _companyUrl;
}
