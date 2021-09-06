<script>
    export let discountType;
    export let discount;

    const discountTypeLowercase = discountType.toLowerCase();
</script>

{#if discountTypeLowercase === 'fixed'}
    -${discount}
{:else if discountTypeLowercase === 'percentage'}
    {#if discount === 100}
        free
    {:else}
        {discount}% off
    {/if}
{:else}
    Unknown discount type {discountType}!
{/if}

<style lang="scss">

</style>
