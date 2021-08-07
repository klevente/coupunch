<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';

    const dispatch = createEventDispatcher();

    export function open(customerToDisplay) {
        customer = customerToDisplay;
        modalOpen = true;
    }

    export function close() {
        modalOpen = false;
        customer = null;
    }

    const onAcceptClick = () => dispatch('accept', customer);

    let modalOpen = false;
    let customer;
</script>

<Modal bind:open={modalOpen} noClickaway>
    {#if !!customer}
        <Dialog title="Found user: {customer.username}">
            <p>Found {customer.username}.</p>
            {#if customer.newlyAdded}
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
