<script>
    import { H3, Label, Divider } from 'attractions';
    import CouponProgress from '../../../../../components/coupon-progress.svelte';
    import DiscountDisplay from '../../../../../components/discount-display.svelte';
    import RewardTable from './reward-table.svelte';
    import { toIndexArray } from 'frontend-library/util/form';

    export let rewards;
    export let type;
    export let redeemLevel;
    const indices = toIndexArray(rewards);
</script>

<H3>Rewards</H3>
{#each indices as index}
    <div class="reward padded" class:current={redeemLevel === index}>
        <div class="reward-title">
            <Label>Level {index + 1} at
                <CouponProgress {type} progress={rewards[index].threshold}/>
                ,
                <DiscountDisplay
                        discountType={rewards[index].discountType}
                        discount={rewards[index].discount}/>
            </Label>
        </div>
        <RewardTable items={rewards[index].items}/>
        <Divider/>
    </div>
{/each}

<style lang="scss">
  @use 'theme' as vars;

  .current {
    background-color: vars.$success;
  }

  .reward {
    border-radius: vars.$snackbar-radius;
    margin-bottom: 0.5em;
    padding-top: 0.5em;
  }
</style>
