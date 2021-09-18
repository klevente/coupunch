<script>
    import CouponTypeChoice from './coupon-type-choice.svelte';
    import PointDisplay from './point-display.svelte';
    import PriceDisplay from './price-display.svelte';

    export let progress;
    export let type;
</script>

<CouponTypeChoice {type}>
    <svelte:fragment slot="point">
        <PointDisplay points={progress}/>
    </svelte:fragment>
    <svelte:fragment slot="price">
        <PriceDisplay price={progress}/>
    </svelte:fragment>
</CouponTypeChoice>

<style lang="scss">

</style>
