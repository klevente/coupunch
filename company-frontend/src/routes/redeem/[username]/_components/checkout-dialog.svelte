<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, Button, Card, Label } from 'attractions';

    const dispatch = createEventDispatcher();

    let coupons = [];

    export function open(redeemedCoupons) {
        coupons = redeemedCoupons;
        console.log(coupons);
        modalOpen = true;
    }

    function close() {
        modalOpen = false;
    }

    function closeAndSignal() {
        close();
        dispatch('close', {});
    }

    let modalOpen = false;
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog
            title="Redeemed Rewards"
    >
        {#each coupons as coupon}
            <div class="coupon-card">
                <Card>
                    <Label>{coupon.couponName}</Label>
                    <div>{coupon.name}</div>
                    <div><span class="amount">1</span> for free</div>
                </Card>
            </div>
        {/each}
        <div class="button-bar">
            <Button outline on:click={close}>Cancel</Button>
            <Button filled on:click={closeAndSignal}>Finish</Button>
        </div>
    </Dialog>
</Modal>

<style src="../../../../../static/css/routes/redeem/[username]/_components/checkout-dialog.scss"></style>

