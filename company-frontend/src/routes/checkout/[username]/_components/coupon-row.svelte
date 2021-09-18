<script>
    import { createEventDispatcher } from 'svelte';
    import { Badge } from 'attractions';
    import { Row, Column } from 'frontend-library/components/table';

    const dispatch = createEventDispatcher();

    export let coupon;

    function onClick() {
        if (!coupon.redeemed) {
            dispatch('click');
        }
    }
</script>

<Row clickable={coupon.redeemable} on:click={onClick}>
    <Column>{coupon.name}</Column>
    <Column>{coupon.type}</Column>
    <Column>
        {#if coupon.redeemable}
            <Badge>Level: {coupon.redeemLevelToDisplay}</Badge>
        {:else}
            Level: {coupon.redeemLevelToDisplay}
        {/if}
    </Column>
</Row>

<style lang="scss">
</style>
