// taken from https://codeburst.io/throttling-and-debouncing-in-javascript-b01cad5c8edf
export function throttlePromise(f, wait = 1000) {
    let inThrottle;
    return (...args) => {
        return new Promise((resolve) => {
            if (!inThrottle) {
                inThrottle = true;
                setTimeout(() => inThrottle = false, wait);
                resolve(f(...args));
            }
        });
    }
}

// taken from https://codeburst.io/throttling-and-debouncing-in-javascript-b01cad5c8edf
export function throttlePromiseWithFinal(f, wait = 1000) {
    let lastFn;
    let lastRan;
    return async (...args) => {
        if (!lastRan) {
            lastRan = Date.now();
            return await f(...args);
        } else {
            clearTimeout(lastFn);
            return await new Promise((resolve) => {
                lastFn = setTimeout(() => {
                    if ((Date.now()) - lastRan >= wait) {
                        lastRan = Date.now();
                        resolve(f(...args));
                    }
                }, wait - (Date.now() - lastRan));
            });
        }
    }
}
