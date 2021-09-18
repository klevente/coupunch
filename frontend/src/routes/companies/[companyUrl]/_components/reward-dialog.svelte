<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button, Divider } from 'attractions';
    import RewardRow from './reward-row.svelte';

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
            {#each coupon.rewards[coupon.redeemLevel].mergedProducts as reward}
                <div on:click={() => onRewardClick(reward)}>
                    <RewardRow {reward}/>
                    <Divider/>
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
