<script>
    import { stores } from '@sapper/app';
    import { Headline, Subhead } from 'attractions';
    import { AwardIcon, GiftIcon, CreditCardIcon, BarChartIcon } from 'svelte-feather-icons';
    import MenuCard from '../../components/menu-card.svelte';

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
            title="Checkout"
            subtitle="Check out customers here"
            href="./checkout"
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
