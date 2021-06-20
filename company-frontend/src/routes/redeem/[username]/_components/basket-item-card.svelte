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

<style src="../../../../../static/css/routes/redeem/[username]/_components/basket-item-card.scss"></style>
