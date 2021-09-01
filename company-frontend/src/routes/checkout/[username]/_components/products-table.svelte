<script>
    import { createEventDispatcher } from 'svelte';
    import DynamicTable from 'frontend-library/components/dynamic-table.svelte';
    import { Row, Column } from 'frontend-library/components/table';
    import SearchField from 'frontend-library/components/search-field.svelte';

    const dispatch = createEventDispatcher();

    export let products;
    export let searchTerm;
    export let sortBy;

    const onProductClick = product => dispatch('rowClick', product);
</script>

<div class="products-table">
    <SearchField {searchTerm}/>
    <DynamicTable data={products} {sortBy} columns={[
        { name: 'Name', property: 'name' },
        { name: 'Price', property: 'price' }
    ]}>
        <Row
                slot="row" let:row
                clickable
                on:click={() => onProductClick(row)}
        >
            <Column>{row.name}</Column>
            <Column>${row.price}</Column>
        </Row>
    </DynamicTable>
</div>

<style lang="scss">

</style>
