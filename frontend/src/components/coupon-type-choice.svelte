<script>
    export let type;

    const lowercaseType = type.toLowerCase();
</script>

{#if lowercaseType === 'point'}
    <slot name="point"></slot>
{:else if lowercaseType === 'price'}
    <slot name="price"></slot>
{:else}
    <slot name="unknown">
        unknown coupon type!
    </slot>
{/if}

<style lang="scss">

</style>
