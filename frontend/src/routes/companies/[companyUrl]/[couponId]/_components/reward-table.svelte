<script>
    import { createEventDispatcher } from 'svelte';
    import { EasyTable, Row, Column } from 'frontend-library/components/table';
    import { sortedReactive } from 'frontend-library/viewmodel/transformations';
    import { sortByStore } from 'frontend-library/viewmodel/transformations/stores';

    const dispatch = createEventDispatcher();

    export let reward;

    const onRowClick = productGroup => dispatch('group-click', productGroup);

    const sortBy = sortByStore('name');
    $: sortedItems = sortedReactive({
        array: reward.items,
        sortBy: $sortBy
    });

</script>

<EasyTable
        {sortBy}
        columns={[
            { name: 'Name', property: 'name' },
            { name: 'Amount', property: 'amount' },
            { name: 'Price' },
            { name: 'New Price' }
        ]}
        items={sortedItems}
>
    <Row slot="row" let:row clickable={row.type === 'group'} on:click={() => onRowClick(row)}>
        <Column>{row.name}</Column>
        <Column>{row.amount}</Column>
        <Column>
            {#if row.type === 'group'}
                Tap
            {:else}
                ${row.originalPrice}
            {/if}
        </Column>
        <Column>
            {#if row.type === 'group'}
                Tap
            {:else}
                <s>${row.discountedPrice}</s>
            {/if}
        </Column>
    </Row>
</EasyTable>

<style lang="scss">

</style>
