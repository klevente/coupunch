export function isArray(obj) {
    return Array.isArray(obj);
}

export function isEmpty(array) {
    return isArray(array) && !array.length;
}

export function generate(size, generator) {
    return Array(size)
        .fill(undefined)
        .map((_, i) => generator(i));
}

export function asArray(obj) {
    return isArray(obj) ? obj : [obj];
}
