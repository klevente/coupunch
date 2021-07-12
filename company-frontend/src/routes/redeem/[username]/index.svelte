<script context="module">
    export async function preload(page, session) {
        return page.params;
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { goto, stores } from '@sapper/app';
    import { Button, H1, H2, Label, Loading } from 'attractions';
    import { ChevronLeftIcon } from 'svelte-feather-icons';
    import ProductCard from './_components/product-card.svelte';
    import CouponCard from './_components/coupon-card.svelte';
    import BasketItemCard from './_components/basket-item-card.svelte';
    import CheckoutDialog from './_components/checkout-dialog.svelte';
    import userService from '../../../services/user-service';
    import productService from '../../../services/product-service';
    import couponService from '../../../services/coupon-service';

    import { writable } from 'svelte/store';

    const { session } = stores();
    const { companyUrl, companyName } = $session.user;

    export let username;
    const user = userService.selectedUser;

    const products = productService.products;
    const coupons = couponService.couponsForUser
    const basket = writable([]);

    let checkoutDialog;

    onMount(async () => {
        await userService.fetchByUsername(companyUrl, username);
        await productService.fetch(companyUrl);
        await couponService.fetchForUser(companyUrl, username);
    });

    function onBackClicked() {
        goto('/redeem');
    }

    function onBasketItemAdded({ detail: { product } }) {
        const index = $basket.findIndex(i => i.id === product.id);
        if (index > -1) {
            $basket[index].amount = $basket[index].amount + 1;
        } else {
            const item = {
                ...product,
                amount: 1,
                type: 'purchase'
            }
            basket.update(basket => [...basket, item]);
        }
    }

    function onBasketItemRemoved({ detail: { item } }) {
        basket.update(basket => basket.filter(i => i !== item));
    }

    function onRedeemClicked({ detail: { coupon } }) {
        console.log(coupon);
        if (coupon.redeemed) {
            const item = {
                couponId: coupon.id,
                couponName: coupon.name,
                ...coupon.reward,
                amount: 1,
                type: 'redeem'
            }
            basket.update(basket => [...basket, item]);
        } else {
            basket.update(basket => basket.filter(i => i.couponId !== coupon.id))
        }
    }

    function checkout() {
        const redeemedCoupons = $basket.filter(i => i.type === 'redeem');
        checkoutDialog.open(redeemedCoupons);
    }

    function navigateToHome() {
        goto('/');
    }
</script>


<svelte:head>
    <title>Redeem :: {companyName}</title>
</svelte:head>

<header>
    <H1>Redeem Coupon</H1>
</header>
<article>
    <div class="checkout">
        {#if $user.isLoading()}
            <Loading/>
        {/if}
        {#if $user.hasData()}
            <div class="checkout-header">
                <Button round neutral class="flex-mr" on:click={onBackClicked}>
                    <ChevronLeftIcon size="20"/>
                </Button>
                <H2>Checkout for {$user.data.name} ({$user.data.username})</H2>
            </div>
            <div class="checkout-content">
                <div class="products bordered">
                    <Label>Products</Label>
                    {#if $products.isLoading()}
                        <Loading/>
                    {/if}
                    {#if $products.hasData()}
                        <div class="card-container-flex">
                            {#each $products.data as product}
                                <ProductCard {product} on:click={onBasketItemAdded}/>
                            {/each}
                        </div>
                    {/if}
                    {#if $products.isError()}
                        <div>error</div>
                    {/if}
                </div>
                <div class="basket bordered">
                    <Label>Basket</Label>
                    <div class="card-container">
                        {#each $basket as item}
                            <BasketItemCard {item} on:removeItem={onBasketItemRemoved}/>
                        {/each}
                    </div>
                </div>
                <div class="coupons bordered">
                    <Label>Coupons</Label>
                    {#if $coupons.isLoading()}
                        <Loading/>
                    {/if}
                    {#if $coupons.hasData()}
                        <div class="card-container-flex">
                            {#each $coupons.data as coupon}
                                <CouponCard {coupon} on:click={onRedeemClicked}/>
                            {/each}
                        </div>
                    {/if}
                    {#if $coupons.isError()}
                        <div>error</div>
                    {/if}
                </div>
            </div>
            <div class="buttons">
                <Button filled>Load From Register</Button>
                <div class="flex-spacer"></div>
                <Button filled on:click={checkout}>Checkout</Button>
            </div>
            <CheckoutDialog bind:this={checkoutDialog} on:close={navigateToHome} />
        {/if}
        {#if $user.isError()}
            <div>error</div>
        {/if}
    </div>
</article>

<style lang="scss">
  @use 'theme' as vars;

  .checkout-header {
    display: flex;
    align-items: center;
  }

  .checkout-content {
    height: 70vh;
    display: grid;
    grid-template-areas: "products basket"
                       "coupons basket";
    grid-template-rows: 60% 40%;
    grid-template-columns: 75% 25%;

    > .products {
      grid-area: products;
    }

    > .basket {
      grid-area: basket;
    }

    > .coupons {
      grid-area: coupons;
    }
  }

  .card-container-flex {
    height: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    overflow-y: auto;
  }

  .card-container {
    height: 100%;
    overflow-y: auto;
  }

  .buttons {
    margin-top: 1em;
    display: flex;
  }

</style>
