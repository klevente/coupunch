<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';

    const dispatch = createEventDispatcher();

    export function open(discountedProductsToDisplay) {
        discountedProducts = discountedProductsToDisplay;
        modalOpen = true;
    }

    export function close() {
        discountedProducts = [];
        modalOpen = false;
    }

    let modalOpen = false;
    let discountedProducts = [];

    const onFinishClick = () => dispatch('finish');
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title="Discounted Items">
        {#each discountedProducts as product}
            <div>
                {product.name} {product.amount} ${product.originalPrice} ${product.discountedPrice}
            </div>
        {/each}
        <div class="button-bar">
            <Button on:click={onFinishClick}>Finish</Button>
        </div>
    </Dialog>
</Modal>

<style lang="scss">

</style>
