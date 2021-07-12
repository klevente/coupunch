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

<style lang="scss">
  @use 'theme' as vars;

  .coupon-card {

    cursor: pointer;

    .info {
      display: flex;
      align-items: center;
    }

    :global .card {
      transition: 150ms box-shadow;

      &:hover {
        box-shadow: vars.$shadow-raised;
      }
    }

    .icon {
      padding: 0.8em;
      border-radius: 50%;
      background: vars.$main;
      color: white;
      align-self: center;

      :global svg {
        display: block;
      }
    }
  }

  .disabled {
    cursor: auto;

    color: vars.$disabled;

    :global .card {
      transition: none;

      &:hover {
        box-shadow: vars.$shadow-0;
      }
    }

    .icon {
      background: vars.$disabled;
    }
  }

  .redeemed {
    :global .card {
      background: vars.$success;
    }
  }
</style>
