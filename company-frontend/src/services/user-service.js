import CompanyService from './company-service';

export default class UserService extends CompanyService {
    static async getCurrent(company, ctx = null) {
        const request = UserService.api
            .endpoint(`${company}/users/current`)
        ctx && request.context(ctx);
        /*request.default(e => { throw e });*/ // try it without this for now
        return await request.get();
    }

    static async login({ company, ...credentials }) {
        return await UserService.api
            .endpoint(`${company}/users/login`)
            .formEncoded()
            .payload(credentials)
            .post();
    }
}
