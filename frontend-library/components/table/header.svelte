<script>
    import Th from './th.svelte';

    export let sortBy = null;
    export let columns;

    function onColumnClick({ detail }) {
        if (!sortBy) {
            return;
        }
        const { property } = $sortBy;
        if (property !== detail) {
            sortBy.setProperty(detail);
        } else {
            sortBy.toggleOrder();
        }
    }
</script>

<thead>
    <tr>
        {#each columns as column}
            <Th {...column} {sortBy} on:click={onColumnClick} />
        {/each}
    </tr>
</thead>

<style lang="scss">

</style>
