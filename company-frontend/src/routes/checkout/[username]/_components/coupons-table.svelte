<script>
    import { createEventDispatcher } from 'svelte';
    import DynamicTable from 'frontend-library/components/dynamic-table.svelte';
    import SearchField from 'frontend-library/components/search-field.svelte';
    import CouponRow from './coupon-row.svelte';

    const dispatch = createEventDispatcher();

    export let coupons;
    export let searchTerm;
    export let sortBy;

    const onCouponClick = coupon => dispatch('rowClick', coupon);
</script>

<div class="coupons-table">
    <SearchField {searchTerm} placeholder="Search coupons..."/>
    <DynamicTable data={coupons} {sortBy} columns={[
        { name: 'Name', property: 'name' },
        { name: 'Type', property: 'type' },
        { name: 'Progress' }
    ]}>
        <div slot="empty">
            The customer currently has no progress in any coupon.
        </div>
        <CouponRow
                slot="row" let:row
                coupon={row}
                on:click={() => onCouponClick(row)}
        />
    </DynamicTable>
</div>

<style lang="scss">

</style>
