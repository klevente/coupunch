import { sleepRandom } from 'frontend-library/util/sleep';

export default class CouponService {
    static async getCompanyCouponsForUser(companyUrl) {
        await sleepRandom();
        return [];
    }

    static async getCompanyCouponForUser(companyUrl, couponId) {
        await sleepRandom();
        return [];
    }
}
