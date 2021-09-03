import Api from '@beyonk/sapper-httpclient';
import { goto } from '@sapper/app';

export function initApi() {
    Api.configure({
        baseUrl: '/api',
        parseErrors: false,
        handlers: {
            accessDenied: async error => {
                console.error('access denied', error);
                await goto('/logout');
            },
            forbidden: error => {
                console.error('forbidden', error);
                throw error;
            },
            badRequest: error => {
                console.error('bad request', error);
                console.log(error.body);
                throw error;
            }
        }
    });
}
