// taken from: https://stackoverflow.com/a/54265129/13181209
export function debouncePromise(f, interval = 300) {
    let timer = null;

    return (...args) => {
        clearTimeout(timer);
        return new Promise((resolve) => {
            timer = setTimeout(
                () => resolve(f(...args)),
                interval,
            );
        });
    };
}
