import { asArray } from '../util/iterable';

export default class AbstractViewmodel {
    get _resource() {
        throw new Error('Must be overridden!');
    }

    get _state() {
        throw new Error('Must be overridden!');
    }

    _defaultServiceCallback(serverResource) {
        this._resource.setSuccess(serverResource);
    }

    _defaultErrorCallback(error) {

    }

    _defaultOperations = {
        array: {
            add: (newResource) => this._resource.updateData(array => [...array, newResource]),
            update: (updatedResource) => this._resource.updateData(array => array.map(resource => resource.id === updatedResource.id ? updatedResource : resource)),
            delete: (deletedResource) => this._resource.updateData(array => array.filter(({ id }) => deletedResource.id !== id)),
        },
        single: {
            update: (updatedResource) => this._resource.updateData(_ => updatedResource),
        }
    }

    async call({
                   stateStore = this._state,
                   action,
                   serviceParams,
                   serviceCallback = this._defaultServiceCallback.bind(this),
                   localCallback,
                   errorCallback = this._defaultErrorCallback.bind(this),
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
            stateStore.emitFailure(e);
            errorCallback(e);
        }
    }

    async load({
                   dataStore = this._resource,
                   serviceCall,
                   serviceParams = [],
               }) {
        try {
            dataStore.setLoading();
            serviceParams = asArray(serviceParams);
            const resource = await serviceCall(...serviceParams);
            dataStore.setSuccess(resource);
        } catch (e) {
            dataStore.setFailure(e);
        }
    }
}
