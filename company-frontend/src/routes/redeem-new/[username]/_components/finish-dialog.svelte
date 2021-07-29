<script>
    import { Dialog, Modal, Button } from 'attractions';

    export function open(discountedProductsToDisplay) {
        discountedProducts = discountedProductsToDisplay;
        modalOpen = true;
    }

    export function close() {
        discountedProducts = null;
        modalOpen = false;
    }

    let modalOpen = false;
    let discountedProducts;
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title="Discounted Items">
        {#each discountedProducts as product}
            <div>{product.name}</div>
            <div>{product.originalPrice}</div>
            <div>{product.discountedPrice}</div>
        {/each}
    </Dialog>
</Modal>

<style lang="scss">

</style>
