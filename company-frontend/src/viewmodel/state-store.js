import { writable } from 'svelte/store';

export const StateStatus = Object.freeze({
    INACTIVE: 'inactive',
    LOADING: 'loading',
    SUCCESS: 'success',
    FAILURE: 'failure',
});

class State {
    #status = StateStatus.INACTIVE;
    #statusText = null;

    get status() {
        return this.#status;
    }

    get statusText() {
        return this.#statusText;
    }

    _emitInactive() {
        this.#status = StateStatus.INACTIVE;
        this.#statusText = null;
        return this;
    }

    _emitLoading() {
        this.#status = StateStatus.LOADING;
        this.#statusText = null;
        return this;
    }

    _emitSuccess(successText) {
        this.#status = StateStatus.SUCCESS;
        this.#statusText = successText;
        return this;
    }

    _emitFailure(error) {
        this.#status = StateStatus.FAILURE;
        this.#statusText = error;
        return this;
    }

    info() {
        return {
            status: this.#status,
            statusText: this.#statusText,
        };
    }
}

export function stateStore() {
    const { subscribe, update } = writable(new State());

    return {
        subscribe,
        emitInactive: () => update(store => store._emitInactive()),
        emitLoading: () => update(store => store._emitLoading()),
        emitSuccess: (successText) => update(store => store._emitSuccess(successText)),
        emitFailure: (error) => update(store => store._emitFailure(error)),
    }
}
