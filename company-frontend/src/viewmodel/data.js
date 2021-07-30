export const Status = {
    Loading: 'LOADING',
    HasData: 'HASDATA',
    Error: 'ERROR'
}

export class Data {
    status = Status.Loading;
    data = undefined;
    error = undefined;

    constructor() {}

    clone() {
        const ret = new Data();
        ret.status = this.status;
        ret.data = this.data;
        ret.error = this.error;
        return ret;
    }

    setData(data) {
        this.data = data;
        this.status = Status.HasData;
        this.error = undefined;
        return this;
    }

    setError(error) {
        this.error = error;
        this.status = Status.Error;
        this.data = undefined;
        return this;
    }

    setLoading() {
        this.status = Status.Loading;
        this.data = undefined
        this.error = undefined;
        return this;
    }

    isLoading() {
        return this.status === Status.Loading;
    }

    isError() {
        return this.status === Status.Error;
    }

    hasData() {
        return this.status === Status.HasData;
    }
}
