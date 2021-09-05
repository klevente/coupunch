import { asArray } from '../util/array';

export default class BaseViewmodel {

    constructor() {
        if (new.target === BaseViewmodel) {
            throw new TypeError(`Cannot construct BaseViewmodel instances directly.`);
        }
    }

    get _resource() {
        console.error('BaseViewmodel._resource() must be overridden to return a datastore.');
        return null;
    }

    get _state() {
        console.error(`BaseViewmodel._state() must be overridden to return a statestore.`);
        return null;
    }

    _defaultOperations(dataStore = this._resource) {
        return {
            array: {
                add: (newResource) => dataStore.updateData(array => [...array, newResource]),
                update: (updatedResource) => dataStore.updateData(array => array.map(resource => resource.id === updatedResource.id ? updatedResource : resource)),
                delete: (deletedResource) => dataStore.updateData(array => array.filter(({ id }) => deletedResource.id !== id)),
            },
            single: {
                update: (updatedResource) => dataStore.updateData(_ => updatedResource),
            },
            nop: (resource) => resource
        };
    }

    async execute({
                      stateStore = this._state,
                      action,
                      serviceParams,
                      serviceCallback = this._defaultServiceCallback(),
                      localCallback = this._defaultOperations().nop,
                      errorCallback = this._defaultErrorCallback(),
                      successCallback = () => {}
                  }) {
        try {
            stateStore || console.warn('No supplied state store found.');
            stateStore && stateStore.emitLoading();
            let shouldUpdateLocally = true;
            serviceParams = asArray(serviceParams);
            const resource = await action.serviceCall(...serviceParams, (...args) => {
                serviceCallback(...args);
                shouldUpdateLocally = false;
            });
            stateStore && stateStore.emitSuccess(action.successText);
            if (shouldUpdateLocally) {
                localCallback(resource);
            }
            successCallback();
        } catch (e) {
            console.error(e);
            stateStore && stateStore.emitFailure(e);
            errorCallback(e);
        }
    }

    async executeCustom({
                            stateStore = this._state,
                            action,
                            serviceParams,
                            successCallback,
                            errorCallback = this._defaultErrorCallback(),
                        }) {
        try {
            stateStore || console.warn('No supplied state store found.');
            stateStore && stateStore.emitLoading();
            serviceParams = asArray(serviceParams);
            const result = await action.serviceCall(...serviceParams);
            stateStore && stateStore.emitSuccess(action.successText);
            successCallback && successCallback(result);
        } catch (e) {
            console.error(e);
            stateStore && stateStore.emitFailure(e);
            errorCallback(e);
        }
    }

    async load({
                   dataStore = this._resource,
                   action,
                   serviceParams = [],
                   successCallback
               }) {
        try {
            dataStore.setLoading();
            serviceParams = asArray(serviceParams);
            const resource = await action.serviceCall(...serviceParams);
            dataStore.setSuccess(resource);
            successCallback && successCallback(resource);
        } catch (e) {
            console.error(e);
            dataStore.setFailure(e);
        }
    }

    _defaultServiceCallback(dataStore = this._resource) {
        return (serverResource) => dataStore.setSuccess(serverResource);
    }

    _defaultErrorCallback() {
        return (error) => {};
    }

    _throwingErrorCallback() {
        return error => { throw error; };
    }
}
