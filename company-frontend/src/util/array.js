import { isIterable } from './iterable';

export function generate(size, generator) {
    return Array(size)
        .fill(undefined)
        .map((_, i) => generator(i));
}

export function asArray(obj) {
    return isIterable(obj) ? obj : [obj];
}
