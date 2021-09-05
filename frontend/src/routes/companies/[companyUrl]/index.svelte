<script context="module">
    export async function preload(page, session) {
        return page.params;
    }
</script>

<script>
    import { onMount } from 'svelte';
    import State from 'frontend-library/components/state.svelte';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import CouponCard from './_components/coupon-card.svelte';
    import Viewmodel from './_viewmodel';
    import { goto, stores } from '@sapper/app';

    export let companyUrl;

    const { session } = stores();

    const viewmodel = new Viewmodel(companyUrl, session);
    const { displayedCoupons, state } = viewmodel;

    onMount(async () => {
        await viewmodel.get();
    });

    $: console.log($displayedCoupons);

    const onCouponClick = ({ id }) => goto(`companies/${companyUrl}/${id}`);

</script>

<Dynamic data={displayedCoupons} iterate>
    <CouponCard
            slot="data" let:item
            coupon={item}
            on:click={() => onCouponClick(item)}
    />
</Dynamic>

<State {state}/>

<style lang="scss">

</style>
