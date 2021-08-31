<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';

    const dispatch = createEventDispatcher();

    export function open(couponToDisplay) {
        coupon = couponToDisplay;
        modalOpen = true;
    }

    export function close() {
        coupon = null;
        modalOpen = false;
    }

    const onRewardClick = (reward) => {
        dispatch('chooseReward', { coupon, reward });
        close();
    };

    let modalOpen = false;
    let coupon;
</script>

<Modal bind:open={modalOpen} noClickaway>
    {#if !!coupon}
        <Dialog title={`Choose Reward for '${coupon.name}' - Level ${coupon.redeemLevelToDisplay}`}>
            {#each coupon.rewards[coupon.redeemLevel].products as reward}
                <div on:click={() => onRewardClick(reward)}>
                    {reward.name} {reward.amount} <s>${reward.originalPrice}</s> ${reward.discountedPrice}
                </div>
            {/each}
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
            </div>
        </Dialog>
    {/if}
</Modal>

<style lang="scss">

</style>
