export function resolve(path, obj = this) {
    return path
        .split('.')
        .reduce((prev, curr) => prev ? prev[curr] : null, obj);
}
