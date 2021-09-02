import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { action, stateStore } from 'frontend-library/viewmodel';
import UserService from '../../services/user-service';

export default class Viewmodel extends BaseViewmodel {
    state = stateStore();

    async register(credentials, goto) {
        await this.executeCustom({
            action: action(UserService.register, 'Successful registration'),
            serviceParams: credentials,
            successCallback: () => {
                goto('login');
            },
            errorCallback: this._throwingErrorCallback()
        })
    }

    get _state() {
        return this.state;
    }
}
