<script>
    import { H3 } from 'attractions';
    import { EasyTable } from 'frontend-library/components/table';
    import { Row, Column } from 'frontend-library/components/table';
    import CouponTypeChoice from '../../../../../components/coupon-type-choice.svelte';
    import EligibleProductGroupProductsDialog from './eligible-product-group-products-dialog.svelte';
    import { sortedReactive } from 'frontend-library/viewmodel/transformations';
    import { sortByStore } from 'frontend-library/viewmodel/transformations/stores';

    export let eligibleItems;
    export let type;

    let productsDialog;

    const onRowClick = productGroup => productsDialog.open(productGroup);

    const sortBy = sortByStore('name');
    $: sortedEligibleItems = sortedReactive({
        array: eligibleItems,
        sortBy: $sortBy
    });
</script>

<H3>Eligible Items</H3>
<CouponTypeChoice {type}>
    <EasyTable
            slot="point"
            {sortBy}
            columns={[
                { name: 'Name', property: 'name' },
                { name: 'Price' },
                { name: 'Points', property: 'points' }
            ]}
            items={sortedEligibleItems}
    >
        <Row slot="row" let:row clickable={row.type === 'group'} on:click={() => onRowClick(row)}>
            <Column>{row.name}</Column>
            <Column>
                {#if row.type === 'group'}
                    Tap
                {:else}
                    ${row.price}
                {/if}
            </Column>
            <Column>{row.points}</Column>
        </Row>
    </EasyTable>
    <EasyTable
            slot="price"
            {sortBy}
            columns={[
                { name: 'Name', property: 'name' },
                { name: 'Price' }
            ]}
            items={sortedEligibleItems}
    >
        <Row slot="row" let:row clickable={row.type === 'group'} on:click={() => onRowClick(row)}>
            <Column>{row.name}</Column>
            <Column>
                {#if row.type === 'group'}
                    Tap
                {:else}
                    ${row.price}
                {/if}
            </Column>
        </Row>
    </EasyTable>
</CouponTypeChoice>

<EligibleProductGroupProductsDialog bind:this={productsDialog}/>

<style lang="scss">

</style>
