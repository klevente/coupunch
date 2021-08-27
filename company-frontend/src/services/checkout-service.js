import { company } from './companyurl';
import BaseService from './base-service';

export default class CheckoutService extends BaseService {
    static async checkout(username, basket) {
        // TODO: handle errors
        await CheckoutService.api
            .endpoint(company(`checkout/${username}`))
            .payload(basket)
            .post();
    }
}
