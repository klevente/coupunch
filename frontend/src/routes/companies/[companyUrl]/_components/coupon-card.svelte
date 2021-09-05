<script>
    import { Card } from 'attractions';
    import ProgressIndicator from '../../../../components/progress-indicator.svelte';
    import CouponType from '../../../../components/coupon-type.svelte'
    import CouponProgress from '../../../../components/coupon-progress.svelte';

    export let coupon;

    const { progress } = coupon;
    const thresholds = coupon.rewards.map(({ threshold }) => threshold);

    const calcProgressForNextLevel = () => {
        const nextLevelThreshold = coupon.rewards[coupon.redeemLevelToDisplay].threshold;
        return nextLevelThreshold - progress;
    };
</script>

<div class="coupon-card" on:click>
    <Card outline>
        <div class="info">
            <CouponType type={coupon.type}/>
            <div class="title">{coupon.name}</div>
            <div class="subtitle">
                Level: {coupon.redeemLevelToDisplay},
                {#if coupon.redeemLevelToDisplay === coupon.rewards.length}
                    you are maxed out!
                {:else}
                    next level after <CouponProgress type={coupon.type} progress={calcProgressForNextLevel()} />
                {/if}
            </div>
        </div>

        <ProgressIndicator value={progress} levels={thresholds}/>
    </Card>
</div>

<style lang="scss">
  @use 'theme' as vars;

  :global .card {
    margin-bottom: 10px
  }

  .info {
    display: grid;
    grid-template-columns: auto 1fr;
    grid-template-rows: auto auto;
    column-gap: 1em;
    margin-bottom: 1em;
  }
</style>
