export default function () {
    const timeout = Math.random() * 5000;
    return new Promise(resolve => setTimeout(resolve, timeout));
}
