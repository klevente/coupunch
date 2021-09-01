<script>
    import { Loading } from 'attractions';

    export let data;
    export let iterate = false;
</script>

{#if $data.loading}
    <slot name="loading">
        <Loading/>
    </slot>
{/if}
{#if $data.success}
    {#if iterate}
        {#each $data.data as item}
            <slot name="data" item={item}/>
        {/each}
    {:else}
        <slot name="data" data={$data.data}/>
    {/if}
{/if}
{#if $data.failure}
    <slot name="error" error={$data.error}>
        <p>{$data.error}</p>
    </slot>
{/if}
