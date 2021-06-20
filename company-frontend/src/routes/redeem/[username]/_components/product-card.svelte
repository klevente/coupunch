<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button } from 'attractions';
    import { BoxIcon, XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let product;

    function onClick() {
        dispatch('click', {
            product
        });
    }

</script>

<div class="product-card flex-mr" on:click={onClick}>
    <Card>
        <div class="icon flex-mr">
            <BoxIcon size="24"/>
        </div>
        <div>
            <div class="name flex-mr">{product.name}</div>
            <div class="subtitle">${product.price}</div>
        </div>
    </Card>
</div>

<style src="../../../../../static/css/routes/redeem/[username]/_components/product-card.scss"></style>
