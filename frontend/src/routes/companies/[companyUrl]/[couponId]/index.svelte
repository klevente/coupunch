<script context="module">
    export async function preload(page, session) {
        return page.params;
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { stores, goto } from '@sapper/app';
    import { H2, Label, Button } from 'attractions';
    import IconButton from 'frontend-library/components/icon-button.svelte';
    import { ChevronLeftIcon } from 'svelte-feather-icons';
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

    const onBackClick = () => goto(`companies/${companyUrl}`);
</script>

<section>
    <Dynamic data={displayedCoupon}>
        <svelte:fragment slot="data" let:data>
            <div class="header">
                <div>
                    <div class="title">
                        <IconButton icon={ChevronLeftIcon} on:click={onBackClick}/>
                        <H2>{data.name}</H2>
                    </div>
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
            <div class="detail">
                <EligibleItemsDisplay
                        type={data.type}
                        eligibleItems={data.eligibleItems}
                />
                <RewardsDisplay
                        redeemLevel={data.redeemLevel}
                        type={data.type}
                        rewards={data.rewards}
                />
            </div>
        </svelte:fragment>
    </Dynamic>
</section>

<style lang="scss">
  .title {
    display: flex;
    align-items: center;

    :global .btn {
      padding-left: 0 !important;
    }
  }

  .header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  .detail {
    margin-top: 10px;
  }
</style>
