<script>
    import { onMount } from 'svelte';
    import { goto, stores } from '@sapper/app';
    import { create } from '@beyonk/sapper-httpclient';

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

<h1>Coupunch logged in home page</h1>
<div>
    You are logged in!
</div>
<div>
    {userData}
</div>

<button on:click={clickForUserData}>Get User Data</button>

<img src="api/users/current/qr" alt="current user qr code">
