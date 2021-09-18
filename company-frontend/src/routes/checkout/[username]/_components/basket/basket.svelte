<script>
    import { EasyTable } from 'frontend-library/components/table';
    import ProductRow from './product-row.svelte';
    import RewardRow from './reward-row.svelte';

    export let items;
</script>

<div class="basket" class:empty={items.length === 0}>
    {#if items.length === 0}
        The basket is empty. Added products and rewards will appear here.
    {:else}
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
    {/if}
</div>


<style lang="scss">
    .empty {
      display: flex;
      text-align: center;
      align-items: center;
    }

    .basket {
      height: 100%;
    }
</style>
