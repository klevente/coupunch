<script>
    import { s } from 'attractions/utils';
    export let progress;
    export let type;

    const typeLowercase = type.toLowerCase();
</script>

{#if typeLowercase === 'point'}
    {progress} point{s(progress)}
{:else if typeLowercase === 'price'}
    ${progress}
{:else}
    unknown coupon type!
{/if}

<style lang="scss">

</style>
