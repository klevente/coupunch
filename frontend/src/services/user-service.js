import FrontendService from './frontend-service';

export default class UserService extends FrontendService {
    static async getCurrent(ctx = null) {
        const request = UserService.api
            .endpoint(`users/current`)
        if (ctx) {
           request.context(ctx);
        }
        return await request.get();
    }

    static async login(credentials) {
        return await UserService.api
            .endpoint(`users/login`)
            .formEncoded()
            .payload(credentials)
            .forbidden(e => { throw e; })
            .post();
    }

    static async register(credentials) {
        await UserService.api
            .endpoint(`users`)
            .payload(credentials)
            .default(e => { throw e; })
            .post();
    }
}
