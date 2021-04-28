<script>
    import { H2, Button, Accordion, AccordionSection } from 'attractions';
    import { PlusIcon, ChevronDownIcon } from 'svelte-feather-icons';
    import ProductCard from './_components/product-card.svelte';
    import ProductGroupCard from './_components/product-group-card.svelte';
    import ConfirmDialog from './../../components/confirm-dialog.svelte';
    import ProductEditDialog from './_components/product-edit-dialog.svelte';

    const products = [
        {
            id: 1,
            name: 'Product 1',
            price: 4.34,
            groups: [
                'default', 'coffee'
            ]
        },
        {
            id: 2,
            name: 'Product 2',
            price: 10.5,
            groups: [
                'default', 'food'
            ]
        }
    ];

    const productGroups = [
        {
            id: 1,
            name: 'default',
        },
        {
            id: 2,
            name: 'coffee',
        },
        {
            id: 3,
            name: 'food',
        }
    ];

    let productEditDialog;
    let productDeleteDialog;
    let productGroupDeleteDialog;

    function openProductEditDialog({ detail: { product } }) {
        productEditDialog.open(product);
    }

    function openProductDeleteDialog({ detail: { product } }) {
        productDeleteDialog.open(product);
    }

    function openProductGroupDeleteDialog({ detail: { productGroup } }) {
        productGroupDeleteDialog.open(productGroup);
    }

    function deleteProduct({ detail: { extra } }) {
        console.log(extra);
    }

    function deleteProductGroup({ detail: { extra } }) {
        console.log(extra);
    }

</script>

<svelte:head>
    <title>Products</title>
</svelte:head>

<main>
    <Accordion multiple>
        <AccordionSection let:toggle>
            <div slot="handle">
                <Button on:click={toggle}>
                    <H2>Product Groups</H2>
                    <ChevronDownIcon size="20" class="ml accordion-chevron" />
                </Button>
            </div>
            <div>
                <span>
                    <Button style="display: inline" filled>
                        <PlusIcon size="20"/>
                        Add
                    </Button>
                </span>
                <div class="product-group-container">
                    {#each productGroups as productGroup}
                        <ProductGroupCard
                                {productGroup}
                                on:deleteProductGroup={openProductGroupDeleteDialog}
                        />
                    {/each}
                </div>
            </div>
        </AccordionSection>
        <AccordionSection let:toggle open>
            <div slot="handle">
                <Button on:click={toggle}>
                    <H2>Products</H2>
                    <ChevronDownIcon size="20" class="ml accordion-chevron" />
                </Button>
            </div>
            <div>
                <span>
                <Button style="display: inline" filled on:click={openProductEditDialog}>
                    <PlusIcon size="20"/>
                    Add
                </Button>
                </span>
                <div class="product-container">
                    {#each products as product}
                        <ProductCard
                                {product}
                                on:editProduct={openProductEditDialog}
                                on:deleteProduct={openProductDeleteDialog}
                        />
                    {/each}
                </div>
            </div>
        </AccordionSection>
    </Accordion>

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

    <ProductEditDialog bind:this={productEditDialog}/>
</main>

<style src="../../../static/css/routes/products/index.scss"></style>
