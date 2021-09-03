import { sleepRandom } from 'frontend-library/util/sleep';

const userCompanies = [
    {
        name: 'Company 1',
        url: 'company1'
    },
    {
        name: 'Coupon Manager',
        url: 'couponmanager'
    }
]

export default class CompanyService {
    static async getUserCompanies() {
        await sleepRandom();
        return [...userCompanies];
    }
}
