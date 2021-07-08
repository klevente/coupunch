<script>
    import { createEventDispatcher } from 'svelte';
    import { Button, Loading } from 'attractions';
    import { XIcon, EditIcon } from 'svelte-feather-icons';
    import Dynamic from '../../../components/dynamic.svelte';

    const dispatch = createEventDispatcher();

    export let productGroups;
    export let defaultGroup;
    export let selectedGroup;

    function onGroupClick(productGroup) {
        dispatch('changeProductGroup', productGroup);
    }

    function onAddClick() {
        dispatch('addProductGroup');
    }

    function onEditClick(productGroup) {
        dispatch('editProductGroup', productGroup);
    }

    function onDeleteClick(productGroup) {
        dispatch('deleteProductGroup', productGroup);
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
    <Dynamic data={productGroups}>
        <div class="product-item" slot="data" let:item>
            <Button
                    on:click={() => onGroupClick(item)}
                    selected={$selectedGroup.id === item.id}
            >
                {item.name}
            </Button>
            <div class="flex-spacer"></div>
            <Button round neutral on:click={() => onEditClick(item)}>
                <EditIcon size="20"/>
            </Button>
            <Button round neutral on:click={() => onDeleteClick(item)}>
                <XIcon size="20"/>
            </Button>
        </div>
    </Dynamic>
</nav>

<style src="../../../../static/css/routes/products/_components/product-group-list-desktop.scss"></style>
