<script>
    import { Dot } from 'attractions';
    import { Row, Column } from '../../../../components/table';

    export let coupon;
</script>

<Row clickable={coupon.redeemable} on:click>
    <Column>{coupon.name}</Column>
    <Column>Rewards: {coupon.reward.name}</Column>
    <Column>
        Progress: {coupon.actual}/{coupon.required}
        {#if coupon.redeemable}
            <Dot success />
        {/if}
    </Column>
</Row>

<style lang="scss">
</style>
