<script>
    import { isIterable } from '../util/iterable';
    import { Loading } from 'attractions';

    export let data;
</script>

{#if $data.loading}
    <Loading />
{/if}
{#if $data.success}
    <div class="data">
        {#if isIterable($data.data)}
            {#each $data.data as item}
                <slot name="data" item={item} />
            {/each}
        {:else}
            <slot name="data" item={$data.data} />
        {/if}
    </div>
{/if}
{#if $data.failure}
    <div class="error">
        <slot name="error" error={$data.error}>
            <p>{$data.error}</p>
        </slot>
    </div>
{/if}
