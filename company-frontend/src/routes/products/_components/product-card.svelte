<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button } from 'attractions';
    import { BoxIcon, XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let product;

    function onEditClick() {
        dispatch('editProduct', {
            product
        });
    }

    function onDeleteClick() {
        dispatch('deleteProduct', {
            product
        });
    }

</script>

<div class="product-card">
    <Card>
        <div class="main-content">
            <div class="icon">
                <BoxIcon size="24" />
            </div>
            <div class="name">{product.name}</div>
            <div class="groups">
                {#each product.groups as group}
                    <div>{group}</div>
                {/each}
            </div>
            <div class="price">${product.price}</div>
        </div>
        <div class="button-bar">
            <Button round neutral on:click={onEditClick}><EditIcon size="20" /></Button>
            <Button round neutral on:click={onDeleteClick}><XIcon size="20" /></Button>
        </div>
    </Card>
</div>

<style src="../../../../static/css/routes/products/_components/product-card.scss"></style>
