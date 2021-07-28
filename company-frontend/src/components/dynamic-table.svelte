<script>
    import { Table, Header, Body } from './table';
    import Dynamic from './dynamic.svelte';

    export let data;
    export let sortBy = null;
    export let columns;
</script>

<Dynamic {data}>
    <svelte:fragment slot="data" let:data>
        <Table>
            <Header {sortBy} {columns}/>
            <Body {data}>
            <svelte:fragment slot="row" let:row>
                <slot name="row" {row}></slot>
            </svelte:fragment>
            </Body>
        </Table>
    </svelte:fragment>
</Dynamic>

<style lang="scss">

</style>
