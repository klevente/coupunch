<script>
    import { onMount } from 'svelte';
    import { H1 } from 'attractions';
    import Viewmodel from './_viewmodel';
    import Dynamic from '../../components/dynamic.svelte';
    import State from '../../components/state.svelte';
    import CouponCard from './_components/coupon-card.svelte';
    import CouponEditDialog from './_components/coupon-edit-dialog.svelte';
    import ConfirmDialog from '../../components/confirm-dialog.svelte';

    const viewmodel = new Viewmodel();
    const { coupons, state } = viewmodel;

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
    <State {state}/>

    <Dynamic data={coupons}>
        <svelte:fragment slot="data" let:item>
            <CouponCard
                    coupon={item}
                    on:edit={openEditDialog}
                    on:delete={openDeleteDialog}
            />
        </svelte:fragment>
        <svelte:fragment slot="error" let:error>
            <p>{error}</p>
        </svelte:fragment>
    </Dynamic>

    <CouponEditDialog
            bind:this={couponEditDialog}
            on:add={addCoupon}
            on:update={updateCoupon}/>

    <ConfirmDialog
            bind:this={couponDeleteDialog}
            title="Delete coupon?"
            on:yes={deleteCoupon}
    >
        Are you sure you want to delete this coupon?
    </ConfirmDialog>
</section>

<style lang="scss"></style>
