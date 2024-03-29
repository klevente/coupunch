<script context="module">
    export async function preload(page, session) {
        return { ...page.params, ...page.query };
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { goto } from '@sapper/app';
    import { Tabs, H2, Button } from 'attractions';
    import State from 'frontend-library/components/state.svelte';
    import Basket from './_components/basket/basket.svelte';
    import ProductsTable from './_components/products-table.svelte';
    import CouponsTable from './_components/coupons-table.svelte';
    import RewardDialog from './_components/reward-dialog.svelte';
    import FinishDialog from './_components/finish-dialog.svelte';
    import IconButton from 'frontend-library/components/icon-button.svelte';
    import { ChevronLeftIcon } from 'svelte-feather-icons';

    import Viewmodel from './_viewmodel';

    export let username;
    export let rewards = null;

    const viewmodel = new Viewmodel();
    const {
        state,
        basket,
        displayedProducts,
        displayedCoupons,
        productSearchTerm,
        couponSearchTerm,
        productSortBy,
        couponSortBy
    } = viewmodel;

    let selectedTab = 'Products';
    let rewardDialog;
    let finishDialog;

    onMount(async () => {
        await viewmodel.getAll(username);
        viewmodel.initBasket(rewards);
    });

    const onBackClick = () => goto('checkout');
    const onProductClick = ({ detail }) => viewmodel.addProductToBasket(detail);
    const onCouponClick = ({ detail }) => rewardDialog.open(detail);
    const onChooseReward = ({ detail: { coupon, reward } }) => viewmodel.redeemCoupon(coupon, reward);
    const onProductRemoveClick = ({ detail }) => viewmodel.removeProductFromBasket(detail);
    const onRewardRemoveClick = ({ detail }) => viewmodel.removeRewardFromBasket(detail);
    const openFinishDialog = (discountedProducts) => finishDialog.open(discountedProducts);
    const onCheckoutClick = () => viewmodel.checkout(username, openFinishDialog);
    const onFinish = () => goto('/');
    const onResendClick = () => viewmodel.manuallyAddCompanyToUsersList(username)

    $: console.log($basket);
</script>

<section>
    <div class="redeem-header">
        <div class="title">
            <IconButton icon={ChevronLeftIcon} on:click={onBackClick}/>
            <H2>Redeem for {username}</H2>
        </div>
        <Tabs
                name="page"
                items={['Products', 'Coupons']}
                bind:value={selectedTab}
        />
        <!--<div class="flex-spacer"></div>-->
        <div class="buttons">
            <Button filled on:click={onResendClick}>Resend</Button>
            <Button filled on:click={onCheckoutClick}>Checkout</Button>
        </div>
    </div>

    <div class="container">
        <div class="left">
            {#if selectedTab === 'Products'}
                <ProductsTable
                        products={displayedProducts}
                        searchTerm={productSearchTerm}
                        sortBy={productSortBy}
                        on:rowClick={onProductClick}
                />
            {:else}
                <CouponsTable
                        coupons={displayedCoupons}
                        searchTerm={couponSearchTerm}
                        sortBy={couponSortBy}
                        on:rowClick={onCouponClick}
                />
            {/if}
        </div>
        <div class="right">
            <Basket
                    items={$basket}
                    on:removeProduct={onProductRemoveClick}
                    on:removeReward={onRewardRemoveClick}
            />
        </div>
    </div>

    <RewardDialog
            bind:this={rewardDialog}
            on:chooseReward={onChooseReward}
    />
    <FinishDialog bind:this={finishDialog} on:finish={onFinish}/>
    <State {state}/>
</section>

<style lang="scss">
  .redeem-header {
    display: flex;
    justify-content: space-between;
  }

  .title {
    display: flex;
    align-items: center;

    :global .btn {
      padding-left: 0 !important;
    }
  }

  .buttons {
    display: flex;
  }

  .container {
    display: flex;
  }

  .left {
    flex: 0 0 60%
  }

  .right {
    flex: 0 0 40%;
  }
</style>
