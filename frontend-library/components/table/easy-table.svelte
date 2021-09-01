<script>
    import Table from './table.svelte';
    import Header from './header.svelte';
    import Body from './body.svelte';

    export let items;
    export let sortBy = null;
    export let columns;
</script>

<Table>
    <Header {sortBy} {columns}/>
    <Body data={items}>
    <svelte:fragment slot="row" let:row>
        <slot name="row" {row}></slot>
    </svelte:fragment>
    </Body>
</Table>

<style lang="scss">

</style>
