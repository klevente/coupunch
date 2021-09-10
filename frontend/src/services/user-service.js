import FrontendService from './frontend-service';

export default class UserService extends FrontendService {
    static async getCurrent(ctx = null) {
        const request = UserService.api
            .endpoint(`users/current`);
        ctx && request.context(ctx);
        return await request.get();
    }

    static async login(credentials) {
        return await UserService.api
            .endpoint(`users/login`)
            .formEncoded()
            .payload(credentials)
            .post();
    }

    static async register(credentials) {
        await UserService.api
            .endpoint(`users`)
            .payload(credentials)
            .post();
    }

    static async update(request) {
        return await UserService.api
            .endpoint(`users/current`)
            .payload(request)
            .put();
    }

    static async updatePassword(request) {
        return await UserService.api
            .endpoint(`users/current/password`)
            .payload(request)
            .put();
    }

    static async updateQr() {
        return await UserService.api
            .endpoint(`users/current/qr`)
            .put();
    }

    static async generateRedeemQr(request) {
        return await UserService.api
            .endpoint(`users/current/qr/redeem`)
            .acceptImage()
            .payload(request)
            .post();
    }
}
