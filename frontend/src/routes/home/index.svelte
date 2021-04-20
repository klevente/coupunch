<script>
    import { goto, stores } from '@sapper/app';
    import { create } from '@beyonk/sapper-httpclient';

    const { session } = stores();

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

    let couponData = undefined;

    async function clickForCouponData() {
        const api = create();
        const res = await api
            .endpoint('couponmanager/coupons/1')
            .accessDenied(e => {
                goto('/logout');
            })
            .get();
        couponData = JSON.stringify(res);
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

<div>
    <div>{couponData}</div>
    <button on:click={clickForCouponData}>Get Test Coupon</button>
</div>
