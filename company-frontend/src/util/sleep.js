const maxTimeout = 1000;

export default function () {
    const timeout = Math.random() * maxTimeout;
    return new Promise(resolve => setTimeout(resolve, timeout));
}
