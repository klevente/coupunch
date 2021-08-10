<script>
    import { isEmpty } from '../../util/array';
    import { toIndexArray } from '../../util/form';

    export let data;
</script>

{#if !data || isEmpty(data)}
    <slot name="empty">
        <div>[placeholder] Empty array</div>
    </slot>
{:else}
    {#each toIndexArray(data) as index}
        <slot name="row" {index}>
            [placeholder] Row {index}
        </slot>
    {/each}
{/if}

<style lang="scss">

</style>
