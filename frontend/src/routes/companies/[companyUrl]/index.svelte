<script context="module">
    export async function preload(page, session) {
        return page.params;
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { goto, stores } from '@sapper/app';
    import { H2, Button } from 'attractions';
    import State from 'frontend-library/components/state.svelte';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import CouponCard from './_components/coupon-card.svelte';
    import RewardDialog from './_components/reward-dialog.svelte';
    import QrDialog from './_components/qr-dialog.svelte';
    import Viewmodel from './_viewmodel';

    export let companyUrl;

    const { session } = stores();

    const viewmodel = new Viewmodel(session, companyUrl);
    const { companyName, displayedCoupons, state } = viewmodel;

    onMount(async () => {
        await viewmodel.getAll();
    });

    let rewardDialog;
    let qrDialog;

    const onCouponClick = ({ id }) => goto(`companies/${companyUrl}/${id}`);
    const onCouponRedeemClick = coupon => rewardDialog.open(coupon);
    const onCouponRedeemed = ({ detail: { coupon, reward } }) => viewmodel.redeemCoupon(coupon, reward);
    const onRedeemCancelClick = (coupon) => viewmodel.cancelRedeem(coupon);
    const onGenerateClick = () => viewmodel.generateQr(src => qrDialog.open(src));
    const onQrDialogClose = () => viewmodel.resetRedeemStatus();

    $: console.log($displayedCoupons);
</script>

<header>
    <Dynamic data={companyName}>
        <H2 slot="data" let:data>{data}'s coupons</H2>
    </Dynamic>
    <Button
            filled
            on:click={onGenerateClick}
            disabled={$displayedCoupons.data?.filter(({ reward }) => !!reward).length === 0}
    >
        Generate QR code for selected rewards
    </Button>
</header>
<article>
    <Dynamic data={displayedCoupons} iterate>
        <CouponCard
                slot="data" let:item
                coupon={item}
                on:click={() => onCouponClick(item)}
                on:redeem={() => onCouponRedeemClick(item)}
                on:cancel={() => onRedeemCancelClick(item)}
        />
    </Dynamic>

    <RewardDialog
            bind:this={rewardDialog}
            on:chooseReward={onCouponRedeemed}
    />

    <QrDialog
            bind:this={qrDialog}
            on:close={onQrDialogClose}
    />
</article>

<State {state}/>

<style lang="scss">
  header {
    margin-bottom: 10px;
  }

  .generate-container {
    margin-bottom: 10px;
  }
</style>
