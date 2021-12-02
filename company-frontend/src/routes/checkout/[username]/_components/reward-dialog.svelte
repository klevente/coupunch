<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';
    import AmountDisplay from 'frontend-library/components/amount-display.svelte';
    import PriceDisplay from 'frontend-library/components/price-display.svelte';

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
                <div class="row" on:click={() => onRewardClick(reward)}>
                    <div class="left">{reward.name}</div>
                    <div class="center">
                        <AmountDisplay amount={reward.amount}/>
                    </div>
                    <div class="right">
                        <PriceDisplay price={reward.originalPrice} discountedPrice={reward.discountedPrice}/>
                    </div>
                </div>
            {/each}
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
            </div>
        </Dialog>
    {/if}
</Modal>

<style lang="scss">
  @use 'theme' as vars;

  .left {
    float: left;
    width: 50%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .center {
    float: left;
    width: 25%;
  }

  .right {
    float: left;
    width: 25%;
    text-align: right;
  }

  .row {
    width: 100%;
    margin-bottom: 10px;
  }

  .row:after {
    content: "";
    display: table;
    clear: both;
  }
</style>
