export function sleepRandom(maxTimeout = 1000) {
    const timeout = Math.random() * maxTimeout;
    return new Promise(resolve => setTimeout(resolve, timeout));
}

export function sleep(timeout = 5000) {
    return new Promise(resolve => setTimeout(resolve, timeout));
}
