<script>
    import { goto, stores } from '@sapper/app';
    import { create } from '@beyonk/sapper-httpclient';
    import { Button, H1, Card, Headline, Subhead } from 'attractions';
    import MenuCard from '../../components/menu-card.svelte';
    import { AwardIcon, GiftIcon, CreditCardIcon, BarChartIcon } from 'svelte-feather-icons';

    const { session } = stores();

    const { username, companyName } = $session.user;
</script>

<svelte:head>
    <title>Home :: {companyName}</title>
</svelte:head>

<header>
    <Headline>Welcome to {companyName}!</Headline>
    <Subhead>You are logged in as {username}.</Subhead>
</header>

<article>
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
</article>

<style lang="scss">
  @use 'theme' as vars;

  header {
    margin-bottom: 2em;
  }

  article {
    height: 50%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    align-content: start;
  }
</style>
