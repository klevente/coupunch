<script>
    import { createEventDispatcher } from 'svelte';
    import { EasyTable, Row, Column } from 'frontend-library/components/table';

    const dispatch = createEventDispatcher();

    export let reward;

    const { products, productGroups } = reward;

    const onRowClick = productGroup => dispatch('group-click', productGroup);
</script>

<EasyTable
        columns={[
            { name: 'Name' },
            { name: 'Amount' },
            { name: 'Price' },
            { name: 'Discounted Price' }
        ]}
        items={productGroups}
>
    <Row slot="row" let:row clickable on:click={() => onRowClick(row)}>
        <Column>{row.name}</Column>
        <Column>{row.amount}</Column>
        <Column>Tap</Column>
        <Column>Tap</Column>
    </Row>
</EasyTable>

<EasyTable
        columns={[
            { name: 'Name' },
            { name: 'Amount' },
            { name: 'Price' },
            { name: 'Discounted Price' }
        ]}
        items={products}
>
    <Row slot="row" let:row>
        <Column>{row.name}</Column>
        <Column>{row.amount}</Column>
        <Column><s>${row.originalPrice}</s></Column>
        <Column>${row.discountedPrice}</Column>
    </Row>
</EasyTable>

<style lang="scss">

</style>
