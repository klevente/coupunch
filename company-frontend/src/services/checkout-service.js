import { company } from './companyurl';
import CompanyService from './company-service';

export default class CheckoutService extends CompanyService {
    static async checkout(username, basket) {
        // TODO: handle errors
        await CheckoutService.api
            .endpoint(company(`checkout/${username}`))
            .payload(basket)
            .post();
    }
}
