<script>
    import { goto, stores } from '@sapper/app';
    import { create } from '@beyonk/sapper-httpclient';
    import { Button, H1 } from 'attractions';

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

<button on:click={click}>Get</button>

<Button filled>hello</Button>
