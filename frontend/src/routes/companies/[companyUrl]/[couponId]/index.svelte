<script context="module">
    export async function preload(page, session) {
        return page.params;
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { stores } from '@sapper/app';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import Viewmodel from './_viewmodel';
    import { H2 } from 'attractions';

    export let companyUrl;
    export let couponId;

    const { session } = stores();

    const viewmodel = new Viewmodel(companyUrl, couponId, session);
    const { coupon } = viewmodel;

    onMount(async () => {
        await viewmodel.get(couponId);
    });

    $: console.log($coupon);
</script>

<section>
    <Dynamic data={coupon}>
        <svelte:fragment slot="data" let:data>
            <H2>{data.name}</H2>
            <p>{data.type}</p>
        </svelte:fragment>
    </Dynamic>
</section>

<style lang="scss">

</style>
