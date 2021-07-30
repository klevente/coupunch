<script context="module">
    export async function preload(page, session) {
        return page.params;
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { Tabs, H2 } from 'attractions';
    import Basket from './_components/basket/basket.svelte';
    import ProductsTable from './_components/products-table.svelte';
    import CouponsTable from './_components/coupons-table.svelte';

    import Viewmodel from './_viewmodel';

    export let username;

    const viewmodel = new Viewmodel();
    const {
        basket,
        displayedProducts,
        displayedCoupons,
        productSearchTerm,
        couponSearchTerm,
        productSortBy,
        couponSortBy
    } = viewmodel;

    let selectedTab = 'Products';

    onMount(async () => {
        await viewmodel.getAll(username);
    });

    const onProductClick = ({ detail }) => viewmodel.addProductToBasket(detail);
    const onCouponClick = ({ detail }) => viewmodel.toggleCouponRedeemStatus(detail);
    const onProductRemoveClick = ({ detail }) => viewmodel.removeProductFromBasket(detail);
    const onRewardRemoveClick = ({ detail }) => viewmodel.removeRewardFromBasket(detail);

    $: console.log($basket);
</script>

<section>
    <div class="redeem-header">
        <H2>Redeem for {username}</H2>
        <Tabs
                name="page"
                items={['Products', 'Coupons']}
                bind:value={selectedTab}
        />
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
</section>

<style lang="scss">
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
