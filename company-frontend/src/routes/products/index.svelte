<script>
    import { onMount } from 'svelte';
    import { stores } from '@sapper/app';
    import { Button, H1 } from 'attractions';
    import State from 'frontend-library/components/state.svelte';
    import SearchField from 'frontend-library/components/search-field.svelte';
    import DynamicTable from 'frontend-library/components/dynamic-table.svelte';
    import ConfirmDialog from 'frontend-library/components/confirm-dialog.svelte';
    import ProductRow from './_components/product-row.svelte';
    import ProductEditDialog from './_components/product-edit-dialog.svelte';
    import ProductGroupEditDialog from './_components/product-group-edit-dialog.svelte';
    import ProductGroupList from './_components/product-group-list.svelte'

    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const {
        displayedProducts,
        productGroups,
        productGroupChips,
        state,
        searchTerm,
        selectedProductGroup,
        sortBy
    } = viewmodel;

    const { session } = stores();
    const { companyName } = $session.user;

    onMount(async () => {
        await viewmodel.getAll();
    });

    let productEditDialog;
    let productDeleteDialog;
    let productGroupEditDialog;
    let productGroupDeleteDialog;

    const openProductEditDialog = ({ detail }) => productEditDialog.open(detail);
    const openProductDeleteDialog = ({ detail }) => productDeleteDialog.open(detail);
    const addProduct = ({ detail }) => viewmodel.addProduct(detail);
    const updateProduct = ({ detail }) => viewmodel.updateProduct(detail);
    const deleteProduct = ({ detail }) => viewmodel.deleteProduct(detail);

    const openProductGroupEditDialog = ({ detail }) => productGroupEditDialog.open(detail);
    const openProductGroupDeleteDialog = ({ detail }) => productGroupDeleteDialog.open(detail);
    const addProductGroup = ({ detail }) => viewmodel.addProductGroup(detail);
    const updateProductGroup = ({ detail }) => viewmodel.updateProductGroup(detail);
    const deleteProductGroup = ({ detail }) => viewmodel.deleteProductGroup(detail);
</script>

<svelte:head>
    <title>Products :: {companyName}</title>
</svelte:head>

<H1>Products</H1>
<section>
    <ProductGroupList
            {productGroups}
            {selectedProductGroup}
            on:add={openProductGroupEditDialog}
            on:edit={openProductGroupEditDialog}
            on:delete={openProductGroupDeleteDialog}
    />
    <div class="product-container">
        <div class="product-header">
            <Button filled on:click={openProductEditDialog}>Add</Button>
            <div class="flex-spacer"></div>
            <SearchField {searchTerm}/>
        </div>

        <DynamicTable data={displayedProducts} {sortBy} columns={[
            { name: 'Icon' },
            { name: 'Name', property: 'name' },
            { name: 'Price', property: 'price' },
            { name: 'Actions' }
        ]}>
            <ProductRow
                    slot="row" let:row
                    product={row}
                    on:edit={openProductEditDialog}
                    on:delete={openProductDeleteDialog}
            />
            <svelte:fragment slot="empty">
                No products match the selected search query.
            </svelte:fragment>
        </DynamicTable>
    </div>

    <ProductEditDialog
            bind:this={productEditDialog}
            productGroups={productGroupChips}
            on:add={addProduct}
            on:update={updateProduct}
    />
    <ProductGroupEditDialog
            bind:this={productGroupEditDialog}
            on:add={addProductGroup}
            on:update={updateProductGroup}
    />

    <ConfirmDialog
            bind:this={productDeleteDialog}
            title="Delete product?"
            on:yes={deleteProduct}
    >
        Are you sure you want to delete this product?
    </ConfirmDialog>

    <ConfirmDialog
            bind:this={productGroupDeleteDialog}
            title="Delete product group?"
            on:yes={deleteProductGroup}
    >
        Are you sure you want to delete this product group?
    </ConfirmDialog>

    <State {state}/>
</section>

<style lang="scss">
  @use 'theme' as vars;

  section {
    display: flex;
  }

  .product-container {
    width: 100%;
  }

  .product-header {
    display: flex;
    margin-bottom: 2em;
  }

  .product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  :global .btn {
    display: flex;
    align-content: center;
  }

  .panel {
    &:not(.open) + section {
      display: none;
    }

    :global .accordion-chevron {
      transition: transform 100ms;
    }

    &.open :global .accordion-chevron {
      transform: rotate(180deg);
    }
  }

</style>
