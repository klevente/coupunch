<script>
    import { createEventDispatcher } from 'svelte';
    import { Button, Loading } from 'attractions';
    import { XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let productGroups;
    export let defaultGroup;
    export let selectedGroup;

    function onGroupClick(productGroup) {
        dispatch('changeProductGroup', {
            productGroup
        });
    }

    function onAddClick() {
        dispatch('addProductGroup', {});
    }

    function onEditClick(productGroup) {
        dispatch('editProductGroup', {
            productGroup
        });
    }

    function onDeleteClick(productGroup) {
        dispatch('deleteProductGroup', {
            productGroup
        });
    }
</script>

<nav class="desktop">
    <Button filled on:click={onAddClick}>
        Add
    </Button>
    <div class="product-item">
        <Button
                on:click={() => onGroupClick(defaultGroup)}
                selected={$selectedGroup.id === defaultGroup.id}
        >
            {defaultGroup.name}
        </Button>
    </div>
    {#if $productGroups.isLoading()}
        <Loading/>
    {/if}
    {#if $productGroups.hasData()}
        {#each $productGroups.data as productGroup}
            <div class="product-item">
                <Button
                        on:click={() => onGroupClick(productGroup)}
                        selected={$selectedGroup.id === productGroup.id}
                >
                    {productGroup.name}
                </Button>
                <div class="flex-spacer"></div>
                <Button round neutral on:click={() => onEditClick(productGroup)}>
                    <EditIcon size="20"/>
                </Button>
                <Button round neutral on:click={() => onDeleteClick(productGroup)}>
                    <XIcon size="20"/>
                </Button>
            </div>
        {/each}
    {/if}
</nav>

<style src="../../../../static/css/routes/products/_components/product-group-list-desktop.scss"></style>
