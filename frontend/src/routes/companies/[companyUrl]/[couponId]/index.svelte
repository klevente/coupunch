<script context="module">
    export async function preload(page, session) {
        return page.params;
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { stores } from '@sapper/app';
    import { H2, Label } from 'attractions';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import ProgressIndicator from '../../../../components/progress-indicator.svelte';
    import CouponProgress from '../../../../components/coupon-progress.svelte';
    import CouponTypeIcon from '../../../../components/coupon-type-icon.svelte';
    import EligibleItemsDisplay from './_components/eligible-items-display.svelte';
    import RewardsDisplay from './_components/rewards-display.svelte';
    import Viewmodel from './_viewmodel';

    export let companyUrl;
    export let couponId;

    const { session } = stores();

    const viewmodel = new Viewmodel(session, companyUrl, couponId);
    const { displayedCoupon } = viewmodel;

    onMount(async () => {
        await viewmodel.get(couponId);
    });

</script>

<section>
    <Dynamic data={displayedCoupon}>
        <svelte:fragment slot="data" let:data>
            <div class="header">
                <div>
                    <H2>{data.name}</H2>
                    <Label>Your progress:
                        <CouponProgress type={data.type} progress={data.progress}/>
                    </Label>
                </div>
                <CouponTypeIcon type={data.type}/>
            </div>
            <ProgressIndicator
                    value={data.progress}
                    levels={data.rewards.map(({ threshold }) => threshold)}
            />
            <EligibleItemsDisplay
                    type={data.type}
                    eligibleItems={data.eligibleItems}
            />
            <RewardsDisplay
                    redeemLevel={data.redeemLevel}
                    type={data.type}
                    rewards={data.rewards}
            />
        </svelte:fragment>
    </Dynamic>
</section>

<style lang="scss">
  .header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }
</style>
