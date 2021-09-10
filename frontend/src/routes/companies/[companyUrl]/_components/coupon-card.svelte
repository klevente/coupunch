<script>
    import { createEventDispatcher } from 'svelte';
    import { Button, Card } from 'attractions';
    import ProgressIndicator from '../../../../components/progress-indicator.svelte';
    import CouponTypeIcon from '../../../../components/coupon-type-icon.svelte'
    import CouponProgress from '../../../../components/coupon-progress.svelte';

    const dispatch = createEventDispatcher();

    export let coupon;

    const { progress } = coupon;
    const thresholds = coupon.rewards.map(({ threshold }) => threshold);

    const calcProgressForNextLevel = () => {
        const nextLevelThreshold = coupon.rewards[coupon.redeemLevelToDisplay].threshold;
        return nextLevelThreshold - progress;
    };

    const onRedeemClick = () => dispatch('redeem');
    const onCancelClick = () => dispatch('cancel');
</script>

<div class="coupon-card">
    <Card outline>
        <div class="info">
            <CouponTypeIcon type={coupon.type}/>
            <div class="title">{coupon.name}</div>
            <div class="subtitle">
                Level: {coupon.redeemLevelToDisplay},
                {#if coupon.redeemLevelToDisplay === coupon.rewards.length}
                    you are maxed out!
                {:else}
                    next level after
                    <CouponProgress type={coupon.type} progress={calcProgressForNextLevel()}/>
                {/if}
            </div>
        </div>

        <ProgressIndicator value={progress} levels={thresholds}/>
        <div class="buttons">
            <Button filled on:click>Details</Button>
            {#if !!coupon.reward}
                <Button filled on:click={onCancelClick}>Cancel</Button>
            {:else}
                <Button filled disabled={!coupon.redeemable} on:click={onRedeemClick}>Redeem</Button>
            {/if}
        </div>
        {#if !!coupon.reward}
            <div>
                Reward: {coupon.reward.amount} {coupon.reward.name} for <s>${coupon.reward.originalPrice}</s> ${coupon.reward.discountedPrice}
            </div>
        {/if}
    </Card>
</div>

<style lang="scss">
  @use 'theme' as vars;

  :global .card {
    margin-bottom: 10px;
    padding-bottom: 14px !important;
  }

  .info {
    display: grid;
    grid-template-columns: auto 1fr;
    grid-template-rows: auto auto;
    column-gap: 1em;
    margin-bottom: 1em;
  }

  .buttons {
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
  }
</style>
