import { writable } from 'svelte/store';

const DataStatus = Object.freeze({
    LOADING: 'loading',
    SUCCESS: 'success',
    FAILURE: 'failure',
});

class Data {
    #status = DataStatus.LOADING;
    #data = null;
    #error = null;

    get loading() {
        return this.#status === DataStatus.LOADING;
    }

    get success() {
        return this.#status === DataStatus.SUCCESS;
    }

    get failure() {
        return this.#status === DataStatus.FAILURE;
    }

    get data() {
        return this.#data;
    }

    get error() {
        return this.#error;
    }

    _setLoading() {
        this.#status = DataStatus.LOADING;
        this.#data = null;
        this.#error = null;
        return this;
    }

    _setSuccess(data) {
        this.#status = DataStatus.SUCCESS;
        this.#data = data;
        this.#error = null;
        return this;
    }

    _setFailure(error) {
        this.#status = DataStatus.FAILURE;
        this.#data = null;
        this.#error = error;
        return this;
    }

    _clone() {
        const cloned = new Data();
        cloned.#status = this.#status;
        cloned.#data = this.#data;
        cloned.#error = this.#error;
        return cloned;
    }

    info() {
        return {
            status: this.#status,
            data: this.#data,
            error: this.#error,
        };
    }
}

export function dataStore() {
    const { subscribe, update } = writable(new Data());
    return {
        subscribe,
        setLoading: () => update(store => store._setLoading()),
        setSuccess: (data) => update(store => store._setSuccess(data)),
        updateData: (updater) => update(store => store._setSuccess(updater(store.data))),
        setFailure: (error) => update(store => store._setFailure(error)),
    }
}
