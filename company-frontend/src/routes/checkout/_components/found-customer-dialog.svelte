<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';

    const dispatch = createEventDispatcher();

    export function open(qrResultToStore) {
        qrResult = qrResultToStore;
        modalOpen = true;
    }

    export function close() {
        modalOpen = false;
        qrResult = null;
    }

    const onAcceptClick = () => dispatch('accept', qrResult);

    let modalOpen = false;
    let qrResult;
</script>

<Modal bind:open={modalOpen} noClickaway>
    {#if !!qrResult}
        <Dialog title="Found user: {qrResult.username}">
            <p>Found {qrResult.username}.</p>
            {#if qrResult.newlyAdded}
                <p>This customer will be added to the company's customer list.</p>
            {/if}
            <div class="button-bar">
                <Button on:click={onAcceptClick}>Accept</Button>
                <Button on:click={close}>Cancel</Button>
            </div>
        </Dialog>
    {/if}
</Modal>

<style lang="scss">

</style>
