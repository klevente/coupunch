<script>
    import { EasyTable } from '../../../../../components/table';
    import ProductRow from './product-row.svelte';
    import RewardRow from './reward-row.svelte';

    export let items;

    /*function calculateSum() {
        return items.reduce((acc, curr) => acc + curr.price, 0);
    }*/
</script>

<div class="basket">
    <EasyTable {items} columns={[
        { name: 'Name' },
        { name: 'Price' },
        { name: 'Amount' },
        { name: 'Actions' }
    ]}>
        <svelte:fragment slot="row" let:row>
            {#if row.type === 'purchase'}
                <ProductRow
                        product={row}
                        on:plusProduct
                        on:minusProduct
                        on:removeProduct
                />
            {:else}
                <RewardRow
                        reward={row}
                        on:removeReward
                />
            {/if}
        </svelte:fragment>
    </EasyTable>

    Sum: todo
</div>


<style lang="scss">

</style>
