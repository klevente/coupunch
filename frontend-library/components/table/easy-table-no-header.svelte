<script>
    import Table from './table.svelte';
    import Body from './body.svelte';

    export let items;
</script>

<Table>
    <Body data={items}>
    <svelte:fragment slot="row" let:row>
        <slot name="row" {row}></slot>
    </svelte:fragment>
    </Body>
</Table>

<style lang="scss">

</style>
