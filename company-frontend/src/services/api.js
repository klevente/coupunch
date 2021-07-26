import Api from '@beyonk/sapper-httpclient';

export function initApi() {
    Api.configure({ baseUrl: '/api', parseErrors: false });
}
