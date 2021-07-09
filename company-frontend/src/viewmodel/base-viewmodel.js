import { asArray } from '../util/array';

export default class BaseViewmodel {

    constructor(...services) {
        if (new.target === BaseViewmodel) {
            throw new TypeError(`Cannot construct BaseViewmodel instances directly.`);
        }
        this._initServices(...services);
    }

    get _resource() {
        console.error('BaseViewmodel._resource() must be overridden to return a datastore.');
        return null;
    }

    get _state() {
        console.error(`BaseViewmodel._state() must be overridden to return a statestore.`);
        return null;
    }

    /*_defaultOperations = {
        array: {
            add: (newResource) => this._resource.updateData(array => [...array, newResource]),
            update: (updatedResource) => this._resource.updateData(array => array.map(resource => resource.id === updatedResource.id ? updatedResource : resource)),
            delete: (deletedResource) => this._resource.updateData(array => array.filter(({ id }) => deletedResource.id !== id)),
        },
        single: {
            update: (updatedResource) => this._resource.updateData(_ => updatedResource),
        }
    }*/

    _defaultOperations(dataStore = this._resource) {
        return {
            array: {
                add: (newResource) => dataStore.updateData(array => [...array, newResource]),
                update: (updatedResource) => dataStore.updateData(array => array.map(resource => resource.id === updatedResource.id ? updatedResource : resource)),
                delete: (deletedResource) => dataStore.updateData(array => array.filter(({ id }) => deletedResource.id !== id)),
            },
            single: {
                update: (updatedResource) => dataStore.updateData(_ => updatedResource),
            }
        };
    }

    async execute({
                   stateStore = this._state,
                   action,
                   serviceParams,
                   serviceCallback = this._defaultServiceCallback(),
                   localCallback,
                   errorCallback = this._defaultErrorCallback(),
               }) {
        try {
            stateStore.emitLoading();
            let shouldUpdateLocally = true;
            serviceParams = asArray(serviceParams);
            const resource = await action.serviceCall(...serviceParams, (...args) => {
                serviceCallback(...args);
                shouldUpdateLocally = false;
            });
            stateStore.emitSuccess(action.successText);
            if (shouldUpdateLocally) {
                localCallback(resource);
            }
        } catch (e) {
            console.error(e);
            stateStore.emitFailure(e);
            errorCallback(e);
        }
    }

    async load({
                   dataStore = this._resource,
                   action,
                   serviceParams = [],
               }) {
        try {
            dataStore.setLoading();
            serviceParams = asArray(serviceParams);
            const resource = await action.serviceCall(...serviceParams);
            dataStore.setSuccess(resource);
        } catch (e) {
            console.error(e);
            dataStore.setFailure(e);
        }
    }

    _defaultServiceCallback(dataStore = this._resource) {
        return (serverResource) => dataStore.setSuccess(serverResource);
    }

    _initServices(...services) {
        services.forEach(service => service.updateCompanyUrl());
    }

    _defaultErrorCallback() {
        return (error) => {};
    }
}