<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button } from 'attractions';
    import { ArchiveIcon, XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let productGroup;

    function onEditClick() {
        dispatch('editProductGroup', {
            productGroup
        });
    }

    function onDeleteClick() {
        dispatch('deleteProductGroup', {
            productGroup
        });
    }

</script>

<div class="product-group-card">
    <Card>
        <div class="main-content">
            <div class="icon">
                <ArchiveIcon size="24" />
            </div>
            <div class="name">{productGroup.name}</div>
        </div>
        <div class="button-bar">
            <Button round neutral on:click={onEditClick}><EditIcon size="20" /></Button>
            <Button round neutral on:click={onDeleteClick}><XIcon size="20" /></Button>
        </div>
    </Card>
</div>

<style src="../../../../static/css/routes/products/_components/product-group-card.scss"></style>
