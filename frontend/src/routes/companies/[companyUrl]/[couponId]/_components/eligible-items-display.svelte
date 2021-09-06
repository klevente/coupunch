<script>
    import { H3 } from 'attractions';
    import { EasyTable } from 'frontend-library/components/table';
    import { Row, Column } from 'frontend-library/components/table';
    import CouponTypeChoice from '../../../../../components/coupon-type-choice.svelte';

    import EligibleProductGroupProductsDialog from './eligible-product-group-products-dialog.svelte';

    export let eligibleItems;
    export let type;

    const { products, productGroups } = eligibleItems;

    let productsDialog;

    const onRowClick = productGroup => productsDialog.open(productGroup);
</script>

<H3>Eligible Items</H3>
<EasyTable
        columns={[
            { name: 'Name' },
            { name: 'Price' },
            { name: 'Value' }
        ]}
        items={productGroups}>
    <Row slot="row" let:row clickable on:click={() => onRowClick(row)}>
        <Column>{row.name}</Column>
        <Column>Tap</Column>
        <Column>
            <CouponTypeChoice {type}>
                <svelte:fragment slot="point">{row.points}</svelte:fragment>
                <svelte:fragment slot="price">Tap</svelte:fragment>
            </CouponTypeChoice>
        </Column>
    </Row>
</EasyTable>
<EasyTable
        columns={[
            { name: 'Name' },
            { name: 'Price' },
            { name: 'Value' }
        ]}
        items={products}>
    <Row slot="row" let:row>
        <Column>{row.name}</Column>
        <Column>${row.price}</Column>
        <Column>
            <CouponTypeChoice {type}>
                <svelte:fragment slot="point">{row.points}</svelte:fragment>
                <svelte:fragment slot="price">${row.price}</svelte:fragment>
            </CouponTypeChoice>
        </Column>
    </Row>
</EasyTable>

<EligibleProductGroupProductsDialog bind:this={productsDialog} />

<style lang="scss">

</style>
