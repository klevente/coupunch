<script>
    import { Row, Column } from 'frontend-library/components/table';

    export let company;
</script>

<Row clickable on:click>
    <Column>{company.name}</Column>
</Row>


<style lang="scss">

</style>
