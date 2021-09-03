import FrontendService from './frontend-service';
import { userId } from './current-user';

export default class CouponService extends FrontendService {
    static async getCompanyCouponsForUser(companyUrl) {
        return await CouponService.api
            .endpoint(`${companyUrl}/${userId()}/coupons`)
            .get(({ coupons }) => coupons);
    }

    static async getCompanyCouponForUser(companyUrl, couponId) {
        return await CouponService.api
            .endpoint(`${companyUrl}/${userId()}/coupons/${couponId}`)
            .get();
    }
}
