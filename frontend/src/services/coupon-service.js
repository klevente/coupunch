import FrontendService from './frontend-service';
import { userId } from './current-user';

export default class CouponService extends FrontendService {
    static async getCompanyCouponsForUser(session, companyUrl) {
        return await CouponService.api
            .endpoint(`${companyUrl}/${userId(session)}/coupons`)
            .get(({ coupons }) => coupons);
    }

    static async getCompanyCouponForUser(session, companyUrl, couponId) {
        return await CouponService.api
            .endpoint(`${companyUrl}/${userId(session)}/coupons/${couponId}`)
            .get();
    }
}
