<script>
    import { createEventDispatcher, onMount, onDestroy } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';
    import QrScanner from 'qr-scanner';

    const dispatch = createEventDispatcher();

    let video;
    let qrScanner;

    onMount(() => {
        QrScanner.WORKER_PATH = '/client/qr-scanner-worker.min.js';
        qrScanner = new QrScanner(video, onResult);
    });

    onDestroy(() => {
        qrScanner && qrScanner.destroy();
    })

    export function open() {
        modalOpen = true;
        qrScanner.start();
    }

    export function close() {
        modalOpen = false;
        qrScanner.stop();
    }

    const onResult = result => {
        dispatch('scan', result);
    };

    let modalOpen = false;
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title="Scan Customer QR">
        <video bind:this={video} height="320" width="480">
            Your browser does not support the video tag.
        </video>
        <div class="button-bar">
            <Button on:click={close}>Cancel</Button>
        </div>
    </Dialog>
</Modal>

<style lang="scss">

</style>
