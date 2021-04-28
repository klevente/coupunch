<script>
    import { goto, stores } from '@sapper/app';
    import { create } from '@beyonk/sapper-httpclient';
    import { Button, H1, Card } from 'attractions';
    import MenuCard from '../../components/menu-card.svelte';
    import { AwardIcon, GiftIcon, CreditCardIcon, BarChartIcon } from 'svelte-feather-icons';

    const { session } = stores();

    $: userData = JSON.stringify($session.user);
    $: console.log(userData);

    async function click() {
        const api = create();
        const res = await api
            .endpoint('couponmanager/users/current')
            .accessDenied(e => {
                goto('/logout');
            })
            .get();

        console.log(res);
    }
</script>

<svelte:head>
    <title>Company Home With Login</title>
</svelte:head>

<H1>Coupunch Company logged in home page</H1>
<div>
    You are logged in!
</div>
<div>
    {userData}
</div>

<Button filled on:click={click}>Get</Button>

<div class="main-menu">
    <MenuCard
            icon={AwardIcon}
            title="Redeem"
            subtitle="Redeem coupons here"
            href="./redeem"
    />
    <MenuCard
            icon={GiftIcon}
            title="Coupons"
            subtitle="Edit coupons"
            href="./coupons"
    />
    <MenuCard
            icon={CreditCardIcon}
            title="Products"
            subtitle="Edit products"
            href="./products"
    />
    <MenuCard
            icon={BarChartIcon}
            title="Reports"
            subtitle="View reports"
            href="./reports"
    />
</div>

<style src="../../../static/css/routes/home.scss"></style>
