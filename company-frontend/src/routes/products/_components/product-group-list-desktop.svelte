<script>
    import { createEventDispatcher } from 'svelte';
    import { Button } from 'attractions';
    import { XIcon, EditIcon } from 'svelte-feather-icons';
    import Dynamic from '../../../components/dynamic.svelte';

    const dispatch = createEventDispatcher();

    export let productGroups;
    export let selectedProductGroup;

    function onGroupClick(productGroup) {
        selectedProductGroup.set(productGroup);
    }

    function onAddClick() {
        dispatch('add');
    }

    function onEditClick(productGroup) {
        dispatch('edit', productGroup);
    }

    function onDeleteClick(productGroup) {
        dispatch('delete', productGroup);
    }

    function isSelected(selectedProductGroup, productGroup) {
        return selectedProductGroup && selectedProductGroup.id === productGroup.id;
    }

    function isDefaultSelected(selectedProductGroup) {
        return selectedProductGroup === null;
    }
</script>

<nav class="desktop">
    <Button filled on:click={onAddClick}>
        Add
    </Button>
    <div class="product-group-item">
        <Button
                on:click={() => onGroupClick(null)}
                selected={isDefaultSelected($selectedProductGroup)}
        >
            All products
        </Button>
    </div>
    <Dynamic data={productGroups}>
        <div class="product-group-item" slot="data" let:item>
            <Button
                    on:click={() => onGroupClick(item)}
                    selected={isSelected($selectedProductGroup, item)}
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
