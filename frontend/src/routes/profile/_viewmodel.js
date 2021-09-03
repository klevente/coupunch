import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';
import UserService from '../../services/user-service';

export default class Viewmodel extends BaseViewmodel {
    user = dataStore();
    state = stateStore();

    #actions = {
        get: action(UserService.getCurrent),
        update: action(UserService.update, 'Successfully updated user'),
        updatePassword: action(UserService.updatePassword, 'Successfully updated password'),
        updateQr: action(UserService.updateQr, 'Successfully updated QR-code'),
    }

    async get() {
        await this.load({
            action: this.#actions.get
        });
    }

    async updateCredentials(credentials) {
        await this.execute({
            action: this.#actions.update,
            serviceParams: credentials,
            localCallback: this._defaultOperations().single.update,
            successCallback: () => console.warn('todo force update of session store')
        });
    }

    async updatePassword(password) {
        await this.execute({
            action: this.#actions.updatePassword,
            serviceParams: password,
            localCallback: this._defaultOperations().single.update
        });
    }

    async updateQr() {
        await this.executeCustom({
            action: this.#actions.updateQr,
        });
    }


    get _resource() {
        return this.user;
    }

    get _state() {
        return this.state;
    }
}
