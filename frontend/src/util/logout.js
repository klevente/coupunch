import { goto, stores } from '@sapper/app';

export function logout(redirectTo) {
    const { session } = stores();
    session.set({
        user: {
            authenticated: false
        }
    });
    goto(redirectTo);
}

export function relogin() {
    logout('login');
}
