<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button } from 'attractions';
    import { BoxIcon, XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let product;

    function onEditClick() {
        dispatch('edit', product);
    }

    function onDeleteClick() {
        dispatch('delete', product);
    }

</script>

<div class="product-card">
    <Card>
        <div class="icon flex-mr">
            <BoxIcon size="24"/>
        </div>
        <div class="name flex-mr">{product.name}</div>
        <div class="subtitle">${product.price}</div>
        <div class="flex-spacer"></div>
        <Button round neutral on:click={onEditClick}>
            <EditIcon size="20"/>
        </Button>
        <Button round neutral on:click={onDeleteClick}>
            <XIcon size="20"/>
        </Button>
    </Card>
</div>

<style src="../../../../static/css/routes/products/_components/product-card.scss"></style>
