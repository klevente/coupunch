<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button, Chip } from 'attractions';
    import { BoxIcon, PlusIcon, MinusIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let item;

    function onPlusClick() {
        item.amount = item.amount + 1;
    }

    function onMinusClick() {
        if (item.amount === 1) {
            dispatch('removeItem', {
                item
            });
            return;
        }
        item.amount = item.amount - 1;
    }

</script>

<div class="basket-item-card" class:coupon={item.type === 'redeem'}>
    <Card>
            <div class="name flex-mr">{item.name}</div>
        <Chip small>{item.amount}</Chip>
        {#if item.type === 'purchase'}
            <div class="button-bar">
                <Button round neutral on:click={onPlusClick}>
                    <PlusIcon size="20"/>
                </Button>
                <Button round neutral on:click={onMinusClick}>
                    <MinusIcon size="20"/>
                </Button>
            </div>
        {/if}
    </Card>
</div>

<style lang="scss">
  @use 'theme' as vars;

  .basket-item-card {
    :global .card {
      transition: 150ms box-shadow;
      &:hover {
        box-shadow: vars.$shadow-raised;
      }

      /*display: flex;
      align-items: center;*/
    }
  }

  .coupon {
    :global .card {
      background: vars.$success;
    }
  }
</style>
