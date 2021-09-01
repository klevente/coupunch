<script>
    import { onMount } from 'svelte';
    import { goto, stores } from '@sapper/app';
    import { create } from '@beyonk/sapper-httpclient';
    import { Button, Headline, Subhead } from 'attractions';

    const { session } = stores();

    onMount(async () => {

    });

    $: userData = JSON.stringify($session.user);
    $: console.log(userData);

    async function clickForUserData() {
        const api = create();
        const res = await api
            .endpoint('users/current')
            .accessDenied(e => {
                goto('/logout');
            })
            .get();

        console.log(res);
    }

</script>

<svelte:head>
    <title>Home With Login</title>
</svelte:head>

<header>
    <Headline>Welcome to Coupunch!</Headline>
    <Subhead>You are logged in as ...</Subhead>
</header>
<div>
    You are logged in!
</div>
<div>
    {userData}
</div>

<Button on:click={clickForUserData}>Get User Data</Button>

<img src="api/users/current/qr" alt="current user qr code">

<style lang="scss">

</style>
