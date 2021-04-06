<script>
    import { stores } from '@sapper/app';

    const { session } = stores();

    $: userData = JSON.stringify($session.user);
    $: console.log(userData);
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
