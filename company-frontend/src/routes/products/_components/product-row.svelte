<script>
    import { createEventDispatcher } from 'svelte';
    import { Button } from 'attractions';
    import { BoxIcon, XIcon, EditIcon } from 'svelte-feather-icons';
    import { Row, Column } from '../../../components/table';

    const dispatch = createEventDispatcher();

    export let product;

    const onEditClick = () => dispatch('edit', product);
    const onDeleteClick = () => dispatch('delete', product);
</script>

<Row>
    <Column><BoxIcon size="24"/></Column>
    <Column>{product.name}</Column>
    <Column>${product.price}</Column>
    <Column>
        <div class="button-bar">
            <Button round neutral on:click={onEditClick}>
                <EditIcon size="20"/>
            </Button>
            <Button round neutral on:click={onDeleteClick}>
                <XIcon size="20"/>
            </Button>
        </div>
    </Column>
</Row>

<style lang="scss">

</style>
