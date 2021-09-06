<script>
    import CouponTypeChoice from './coupon-type-choice.svelte';
    import { s } from 'attractions/utils';
    export let progress;
    export let type;

</script>

<CouponTypeChoice {type}>
    <svelte:fragment slot="point">
        {progress} point{s(progress)}
    </svelte:fragment>
    <svelte:fragment slot="price">
        ${progress}
    </svelte:fragment>
</CouponTypeChoice>

<style lang="scss">

</style>
