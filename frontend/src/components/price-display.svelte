<script>
    export let price;
    export let discountedPrice = null;
</script>

{#if discountedPrice != null}
    <s>${price}</s> ${discountedPrice}
{:else}
    ${price}
{/if}

<style lang="scss">

</style>
