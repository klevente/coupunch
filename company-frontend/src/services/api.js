import { goto } from '@sapper/app';
import Api from '@beyonk/sapper-httpclient';

export function initApi() {
    Api.configure({
        baseUrl: '/api',
        parseErrors: false,
        handlers: {
            accessDenied: error => {
                console.error(error);
                goto('/logout');
            },
            default: error => {
                console.error(error);
            }
        }
    });
}
