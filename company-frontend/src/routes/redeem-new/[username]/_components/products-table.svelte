<script>
    import { createEventDispatcher } from 'svelte';
    import DynamicTable from '../../../../components/dynamic-table.svelte';
    import { Row, Column } from '../../../../components/table';
    import SearchField from '../../../../components/search-field.svelte';

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
        <svelte:fragment slot="row" let:row>
            <Row clickable on:click={() => onProductClick(row)}>
                <Column>{row.name}</Column>
                <Column>${row.price}</Column>
            </Row>
        </svelte:fragment>
    </DynamicTable>
</div>

<style lang="scss">

</style>
