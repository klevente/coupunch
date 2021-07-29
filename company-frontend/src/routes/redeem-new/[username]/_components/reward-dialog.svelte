<script>
    import { Dialog, Modal, Button } from 'attractions';

    export function open(couponToDisplay) {
        coupon = couponToDisplay;
        modalOpen = true;
    }

    export function close() {
        coupon = null;
        modalOpen = false;
    }

    let modalOpen = false;
    let coupon;
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title={`Choose Reward for ${coupon.name}`}>
        {#each coupon.rewards as reward}
            <div>{reward.name}</div>
            <div>{reward.amount}</div>
            <div>{reward.originalPrice}</div>
            <div>{reward.discountedPrice}</div>
        {/each}
    </Dialog>
</Modal>

<style lang="scss">

</style>
