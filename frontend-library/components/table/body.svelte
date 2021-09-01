<script>
    export let data;
</script>

<tbody>
{#if $$slots.row}
    {#each data as row}
        <slot name="row" row={row}/>
    {/each}
{:else}
    <slot/>
{/if}
</tbody>

<style lang="scss">

</style>
