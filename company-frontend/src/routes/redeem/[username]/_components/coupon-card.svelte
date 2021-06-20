<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button, Dot } from 'attractions';
    import { AwardIcon, XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let coupon;
    let redeemable = coupon.actual === coupon.required;
    coupon.redeemed = false;

    function onClick() {
        if (redeemable) {
            coupon.redeemed = !coupon.redeemed;
            dispatch('click', {
                coupon
            });
        }
    }

</script>

<div class="coupon-card" class:disabled={!redeemable} class:redeemed={coupon.redeemed} on:click={onClick}>
    <Card>
        <div class="info">
            <div class="icon flex-mr">
                <AwardIcon size="24"/>
            </div>
            <div class="flex-mr">{coupon.name}</div>
            <div>{coupon.actual}/{coupon.required}</div>
        </div>
        <div class="rewards">
            {coupon.reward.name}
        </div>
    </Card>
</div>

<style src="../../../../../static/css/routes/redeem/[username]/_components/coupon-card.scss"></style>
