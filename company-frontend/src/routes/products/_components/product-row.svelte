<script>
    import { createEventDispatcher } from 'svelte';
    import { BoxIcon, XIcon, EditIcon } from 'svelte-feather-icons';
    import IconButton from 'frontend-library/components/icon-button.svelte';
    import { Row, Column } from 'frontend-library/components/table';

    const dispatch = createEventDispatcher();

    export let product;

    const onEditClick = () => dispatch('edit', product);
    const onDeleteClick = () => dispatch('delete', product);
</script>

<Row>
    <!--<Column>
        <BoxIcon size="24"/>
    </Column>-->
    <Column>{product.name}</Column>
    <Column>${product.price}</Column>
    <Column>
        <div class="button-bar">
            <IconButton icon={EditIcon} on:click={onEditClick}/>
            <IconButton icon={XIcon} on:click={onDeleteClick}/>
        </div>
    </Column>
</Row>

<style lang="scss">

</style>
