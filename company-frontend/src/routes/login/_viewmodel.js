import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { action, stateStore } from 'frontend-library/viewmodel';
import UserService from '../../services/user-service';

export default class Viewmodel extends BaseViewmodel {
    state = stateStore();

    async login(credentials, session) {
        await this.executeCustom({
            action: action(UserService.login, 'Successful login'),
            serviceParams: credentials,
            successCallback: currentUser => {
                session.update(session => {
                    session.user = {
                        authenticated: true,
                        ...currentUser
                    };
                    return session;
                });
                window.location.href = 'home';
            },
            errorCallback: this._throwingErrorCallback()
        });
    }

    get _state() {
        return this.state;
    }
}
