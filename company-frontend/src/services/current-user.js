import { get } from 'svelte/store';
import { goto } from '@sapper/app';
import UserService from './user-service';

export function userId(session) {
    const { user: { id } } = get(session);
    return id;
}

export async function fetchAndUpdateCurrentUser(companyUrl, session) {
    console.log('called update');
    try {
        const currentUser = await UserService.getCurrent(companyUrl);
        updateCurrentUser(session, currentUser);
    } catch (e) {
        console.error('error while updating current user', e);
        await goto('/logout');
    }
}

export function updateCurrentUser(session, currentUser) {
    session.update(session => {
        session.user = { ...session.user, ...currentUser };
        return session;
    });
}
