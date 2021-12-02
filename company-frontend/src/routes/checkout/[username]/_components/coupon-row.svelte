<script>
    import { createEventDispatcher } from 'svelte';
    import { Badge } from 'attractions';
    import { AwardIcon, DollarSignIcon } from 'svelte-feather-icons';
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
    <!--<Column>{coupon.type}</Column>-->
    <Column>
        {#if coupon.type === 'POINT'}
            <AwardIcon size="24"/>
        {:else if coupon.type === 'PRICE'}
            <DollarSignIcon size="24"/>
        {:else}
            Unknown coupon type!
        {/if}
    </Column>
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
