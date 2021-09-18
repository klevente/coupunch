<script>
    import PriceDisplay from '../../../../../components/price-display.svelte';
    import { sortedReactiveSimple } from 'frontend-library/viewmodel/transformations';

    export let products;

    $: sortedProducts = sortedReactiveSimple({
        array: products,
        sortProperty: 'name'
    });
</script>

{#each sortedProducts as product}
    <div class="row">
        <div class="left">{product.name}</div>
        <div class="right">
            <PriceDisplay price={product.originalPrice} discountedPrice={product.discountedPrice}/>
        </div>
    </div>
{/each}

<style lang="scss">
  @use 'theme' as vars;

  .row {
    width: 100%;
  }
  .row:after {
    content: "";
    display: table;
    clear: both;
  }

  .left {
    /*width: calc(50% - 0.5em);*/
    float: left;
    width: 50%
  }

  .right {
    float: left;
    width: 50%;
    text-align: right;
  }
</style>
