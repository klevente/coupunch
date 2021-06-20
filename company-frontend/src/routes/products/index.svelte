<script>
    import { onMount } from 'svelte';
    import { stores } from '@sapper/app';
    import { Button, H1, H2, Loading, TextField } from 'attractions';
    import ProductCard from './_components/product-card.svelte';
    import ConfirmDialog from './../../components/confirm-dialog.svelte';
    import ProductEditDialog from './_components/product-edit-dialog.svelte';
    import ProductGroupEditDialog from './_components/product-group-edit-dialog.svelte';
    import ProductGroupListDesktop from './_components/product-group-list-desktop.svelte'
    import ProductGroupListMobile from './_components/product-group-list-mobile.svelte';

    import productService, { defaultGroup } from '../../services/product-service';
    import productGroupService from '../../services/product-group-service';
    import { writable } from 'svelte/store';

    const { session } = stores();
    const { companyUrl, companyName } = $session.user;

    const selectedGroup = writable(defaultGroup);
    const productSearchTerm = writable('');

    const products = productService.filteredProducts(selectedGroup, productSearchTerm);
    const productGroups = productGroupService.productGroups;

    onMount(async () => {
        await productService.fetch(companyUrl);
        await productGroupService.fetch(companyUrl);
    });

    let productEditDialog;
    let productDeleteDialog;
    let productGroupEditDialog;
    let productGroupDeleteDialog;

    function openProductEditDialog({ detail: { product } }) {
        productEditDialog.open(product);
    }

    function openProductDeleteDialog({ detail: { product } }) {
        productDeleteDialog.open(product);
    }

    function openProductGroupEditDialog({ detail: { productGroup } }) {
        productGroupEditDialog.open(productGroup)
    }

    function openProductGroupDeleteDialog({ detail: { productGroup } }) {
        productGroupDeleteDialog.open(productGroup);
    }

    function addProduct({ detail: { product } }) {
        productService.add(companyUrl, product);
    }

    function updateProduct({ detail: { product } }) {
        productService.update(companyUrl, product);
    }

    function deleteProduct({ detail: { extra } }) {
        productService.delete(companyUrl, extra);
    }

    function deleteProductGroup({ detail: { extra } }) {
        console.log(extra);
    }

    function changeSelectedProductGroup({ detail: { productGroup } }) {
        const newlySelectedGroup = productGroup.id === defaultGroup.id ?
            defaultGroup :
            $productGroups.data.find(g => g.id === productGroup.id)
        selectedGroup.set(newlySelectedGroup);
    }

</script>

<svelte:head>
    <title>Products :: {companyName}</title>
</svelte:head>

<H1>Products</H1>
<ProductGroupListMobile
        {productGroups}
        {defaultGroup}
        {selectedGroup}
/>
<section>
    <ProductGroupListDesktop
            {productGroups}
            {defaultGroup}
            {selectedGroup}
            on:changeProductGroup={changeSelectedProductGroup}
            on:addProductGroup={openProductGroupEditDialog}
            on:editProductGroup={openProductGroupEditDialog}
            on:deleteProductGroup={openProductGroupDeleteDialog}
    />
    <div class="product-container">
        <div class="product-header">
            <Button filled on:click={openProductEditDialog}>
                Add
            </Button>
            <div class="flex-spacer"></div>
            <TextField outline type="search" placeholder="Search..." bind:value={$productSearchTerm}/>
        </div>
        {#if $products.isLoading()}
            <Loading/>
        {/if}
        {#if $products.hasData()}
            <div class="product-list">
                {#each $products.data as product}
                    <ProductCard
                            {product}
                            on:editProduct={openProductEditDialog}
                            on:deleteProduct={openProductDeleteDialog}
                    />
                {/each}
            </div>
        {/if}
        {#if $products.isError()}
            <div>Error</div>
        {/if}
    </div>

    <ConfirmDialog
            bind:this={productDeleteDialog}
            title="Delete product?"
            on:yes={deleteProduct}
            on:no={() => console.log('no')}
    >
        Are you sure you want to delete this product?
    </ConfirmDialog>

    <ConfirmDialog
            bind:this={productGroupDeleteDialog}
            on:yes={deleteProductGroup}
            on:no={() => console.log('no')}
            title="Delete product group?"
    >
        Are you sure you want to delete this product group?
    </ConfirmDialog>

    <ProductEditDialog bind:this={productEditDialog}
                       on:addProduct={addProduct}
                       on:updateProduct={updateProduct}
    />
    <ProductGroupEditDialog bind:this={productGroupEditDialog}/>
</section>

<style src="../../../static/css/routes/products/index.scss"></style>
