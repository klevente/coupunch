<script>
    import { createEventDispatcher } from 'svelte';
    import { Dot } from 'attractions';
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
        Redeem level: {coupon.redeemLevelToDisplay}
        {#if coupon.redeemable}
            <Dot success />
        {/if}
    </Column>
</Row>

<style lang="scss">
</style>
