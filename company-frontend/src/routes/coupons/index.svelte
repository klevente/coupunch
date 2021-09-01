<script>
    import { onMount } from 'svelte';
    import { H1, Button } from 'attractions';
    import SearchField from '../../components/search-field.svelte';
    import Viewmodel from './_viewmodel';
    import DynamicTable from '../../components/dynamic-table.svelte';
    import State from '../../components/state.svelte';
    import CouponRow from './_components/coupon-row.svelte';
    import CouponEditDialog from './_components/coupon-edit-dialog.svelte';
    import ConfirmDialog from '../../components/confirm-dialog.svelte';

    const viewmodel = new Viewmodel();
    const {
        displayedCoupons,
        sortBy,
        searchTerm,
        displayedProducts,
        displayedProductGroups,
        productSearchTerm,
        productGroupSearchTerm,
        productSortBy,
        productGroupSortBy,
        state
    } = viewmodel;

    let couponEditDialog, couponDeleteDialog;

    onMount(async () => {
        await viewmodel.get();
    });

    const openEditDialog = ({ detail }) => couponEditDialog.open(detail);
    const openDeleteDialog = ({ detail }) => couponDeleteDialog.open(detail);
    const addCoupon = ({ detail }) => viewmodel.add(detail);
    const updateCoupon = ({ detail }) => viewmodel.update(detail);
    const deleteCoupon = ({ detail }) => viewmodel.delete(detail);
</script>

<svelte:head>
    <title>Coupons</title>
</svelte:head>

<H1>Coupons</H1>
<section>
    <div class="coupon-header">
        <Button filled on:click={openEditDialog}>Add</Button>
        <div class="flex-spacer"></div>
        <SearchField {searchTerm}/>
    </div>
    <DynamicTable data={displayedCoupons} {sortBy} columns={[
        { name: 'Name ', property: 'name' },
        { name: 'Type', property: 'type'},
        { name: 'Eligible Items' },
        { name: 'Reward Levels' },
        { name: 'Actions' }
    ]}>
        <CouponRow
                slot="row" let:row
                coupon={row}
                on:edit={openEditDialog}
                on:delete={openDeleteDialog}
        />
        <svelte:fragment slot="empty">
            No coupons match the selected search query.
        </svelte:fragment>
    </DynamicTable>

    <CouponEditDialog
            bind:this={couponEditDialog}
            products={displayedProducts}
            productGroups={displayedProductGroups}
            {productSearchTerm}
            {productGroupSearchTerm}
            {productSortBy}
            {productGroupSortBy}
            on:add={addCoupon}
            on:update={updateCoupon}/>

    <ConfirmDialog
            bind:this={couponDeleteDialog}
            title="Delete coupon?"
            on:yes={deleteCoupon}
    >
        Are you sure you want to delete this coupon?
    </ConfirmDialog>

    <State {state}/>
</section>

<style lang="scss">
  .coupon-header {
    display: flex;
    margin-bottom: 2em;
  }
</style>
