<script>
    import { createEventDispatcher } from 'svelte';
    import { Label } from 'attractions';
    import { ArrowDownIcon, ArrowUpIcon } from 'svelte-feather-icons';

    export let name;
    export let property = null;
    export let sortBy;

    const dispatch = createEventDispatcher();

    const onHeaderClick = () => {
        if (property) {
            dispatch('click', property);
        }
    };

    let arrowState = null;

    $: {
        if (property) {
            if ($sortBy.property === property) {
                arrowState = $sortBy.order;
            } else {
                arrowState = null;
            }
        }
    }
</script>

<th on:click={onHeaderClick} class:sortable={!!property}>
    <Label>{name}</Label>
    {#if arrowState === 'asc'}
        <ArrowUpIcon size="12"/>
    {:else if arrowState === 'desc'}
        <ArrowDownIcon size="12"/>
    {/if}
</th>

<style lang="scss">
  th {
    text-align: left;
    padding: 0.5em 1em;
  }

  .sortable {
    cursor: pointer;
  }

  :global .label {
    display: inline;
  }
</style>
