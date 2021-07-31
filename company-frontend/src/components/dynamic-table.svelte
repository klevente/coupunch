<script>
    import { Table, Header, Body } from './table';
    import Dynamic from './dynamic.svelte';

    export let data;
    export let sortBy = null;
    export let columns;

    $: empty = $data.empty;
</script>

<Dynamic {data}>
    <svelte:fragment slot="data" let:data>
        {#if $$slots.empty && empty}
            <slot name="empty"/>
        {:else}
            <Table>
                <Header {sortBy} {columns}/>
                <Body {data}>
                <svelte:fragment slot="row" let:row>
                    <slot name="row" {row}/>
                </svelte:fragment>
                </Body>
            </Table>
        {/if}
    </svelte:fragment>
</Dynamic>

<style lang="scss">

</style>
