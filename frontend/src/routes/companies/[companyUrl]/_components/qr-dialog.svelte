<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';

    const dispatch = createEventDispatcher();

    export function open(imageSrc) {
        src = imageSrc;
        modalOpen = true;
    }

    export function close() {
        dispatch('close');
        modalOpen = false;
        src = null;
    }

    let modalOpen = false;
    let src = null;
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title="QR">
        <p>Show this code to the cashier to redeem your coupons with your selected rewards.</p>
        <img class:hidden={!src} {src} alt="qr-code">
        <div class="button-bar">
            <Button on:click={close}>Close</Button>
        </div>
    </Dialog>
</Modal>

<style lang="scss">
    .hidden {
      display: none;
    }
</style>
