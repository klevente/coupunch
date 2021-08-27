import { create } from '@beyonk/sapper-httpclient';
import { company } from './companyurl';

const api = create();

export default class CheckoutService {
    static async checkout(username, basket) {
        // TODO: handle errors
        await api
            .endpoint(company(`checkout/${username}`))
            .payload(basket)
            .post();
    }
}
