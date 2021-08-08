<script>
    import { createEventDispatcher } from 'svelte';
    import { Button } from 'attractions';
    import { XIcon, EditIcon } from 'svelte-feather-icons';
    import Dynamic from '../../../components/dynamic.svelte';

    const dispatch = createEventDispatcher();

    export let productGroups;
    export let selectedProductGroup;

    const onGroupClick = productGroup => selectedProductGroup.set(productGroup);
    const onAddClick = () => dispatch('add');
    const onEditClick = productGroup => dispatch('edit', productGroup);
    const onDeleteClick = productGroup => dispatch('delete', productGroup);

    const isSelected = (selectedProductGroup, productGroup) => selectedProductGroup && selectedProductGroup.id === productGroup.id;
    const isDefaultSelected = selectedProductGroup => selectedProductGroup === null;
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
    <Dynamic data={productGroups} iterate>
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

<style lang="scss">
  @use 'sass:color';
  @use 'theme' as vars;

  nav {
    display: block;
    margin-right: 3em;
    padding: 0.5em 0.5em 0.5em 0;
    min-width: 13em;
    border-right: 1px solid color.adjust(vars.$main, $alpha: -0.75);

    :global .btn {
      white-space: nowrap;
      margin-bottom: 0.25em;
    }

    .product-group-item {
      width: 100%;
      display: flex;
    }
  }
</style>
