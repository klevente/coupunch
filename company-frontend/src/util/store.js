export function isStore(obj) {
    return typeof obj.subscribe === 'function';
}
