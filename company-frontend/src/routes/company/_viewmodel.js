import BaseViewmodel from 'frontend-library/viewmodel/base-viewmodel';
import { dataStore, stateStore, action } from 'frontend-library/viewmodel';
import ConfigService from '../../services/config-service';

export default class Viewmodel extends BaseViewmodel {
    settings = dataStore();
    state = stateStore();

    #actions = {
        get: action(ConfigService.getSettings),
        update: action(ConfigService.updateSettings, 'Successfully updated settings'),
        resend: action(ConfigService.resend, 'Successfully requested update')
    }

    async get() {
        await this.load({
            action: this.#actions.get
        });
    }

    async update(settings, successCallback, errorCallback) {
        await this.execute({
            action: this.#actions.update,
            serviceParams: settings,
            localCallback: this._defaultOperations().single.update(),
            successCallback,
            errorCallback
        });
    }

    async resendSettings() {
        await this.executeCustom({
            action: this.#actions.resend
        });
    }

    get _resource() {
        return this.settings;
    }

    get _state() {
        return this.state;
    }
}
