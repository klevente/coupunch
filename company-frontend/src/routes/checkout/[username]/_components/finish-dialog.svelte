<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';
    import AmountDisplay from 'frontend-library/components/amount-display.svelte';
    import PriceDisplay from 'frontend-library/components/price-display.svelte';

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
        <div class="container">
            {#if discountedProducts.length === 0}
                <div>No discounted items.</div>
            {:else}
                {#each discountedProducts as product}
                    <div class="row">
                        <div class="left">{product.name}</div>
                        <div class="center">
                            <AmountDisplay amount={product.amount}/>
                        </div>
                        <div class="right">
                            <PriceDisplay price={product.originalPrice} discountedPrice={product.discountedPrice}/>
                        </div>
                        <!--{product.name} {product.amount} <s>${product.originalPrice}</s> ${product.discountedPrice}-->
                    </div>
                {/each}
            {/if}
            <div class="button-bar">
                <Button on:click={onFinishClick}>Finish</Button>
            </div>
        </div>
    </Dialog>
</Modal>

<style lang="scss">
  @use 'theme' as vars;

  .container {
    min-width: 350px;
  }

  .left {
    float: left;
    width: 50%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .center {
    float: left;
    width: 25%;
  }

  .right {
    float: left;
    width: 25%;
    text-align: right;
    white-space: nowrap;
  }

  .row {
    width: 100%;
  }

  .row:after {
    content: "";
    display: table;
    clear: both;
  }
</style>
