<script>
    import { Row, Column } from 'frontend-library/components/table';

    export let customer;
</script>

<Row clickable on:click>
    <Column>{customer.name}</Column>
    <Column>{customer.username}</Column>
</Row>

<style lang="scss">

</style>
