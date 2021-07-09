<script>
    import { onMount } from 'svelte';
    import { stores } from '@sapper/app';
    import { Button, H1, TextField } from 'attractions';
    import Dynamic from '../../components/dynamic.svelte';
    import State from '../../components/state.svelte';
    import ProductCard from './_components/product-card.svelte';
    import ConfirmDialog from './../../components/confirm-dialog.svelte';
    import ProductEditDialog from './_components/product-edit-dialog.svelte';
    import ProductGroupEditDialog from './_components/product-group-edit-dialog.svelte';
    import ProductGroupListDesktop from './_components/product-group-list-desktop.svelte'

    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const { categorizedProducts, productGroups, state, searchTerm, selectedProductGroup } = viewmodel;

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
    <ProductGroupListDesktop
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
            <TextField outline type="search" placeholder="Search..." bind:value={$searchTerm}/>
        </div>

        <State {state}/>

        <Dynamic data={categorizedProducts}>
            <svelte:fragment slot="data" let:item>
                <ProductCard
                        product={item}
                        on:edit={openProductEditDialog}
                        on:delete={openProductDeleteDialog}
                />
            </svelte:fragment>
        </Dynamic>
    </div>

    <ProductEditDialog
            bind:this={productEditDialog}
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
</section>

<style src="../../../static/css/routes/products/index.scss"></style>
