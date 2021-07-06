export function isIterable(obj) {
    if (obj == null || typeof(obj) === 'string') {
        return false;
    }
    return typeof obj[Symbol.iterator] === 'function';
}

export function asArray(obj) {
    return isIterable(obj) ? obj : [obj];
}
