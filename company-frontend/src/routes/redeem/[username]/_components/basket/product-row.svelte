<script>
    import { createEventDispatcher } from 'svelte';
    import { PlusIcon, MinusIcon, XIcon } from 'svelte-feather-icons';
    import IconButton from '../../../../../components/icon-button.svelte';
    import { Row, Column } from '../../../../../components/table';

    const dispatch = createEventDispatcher();

    export let product;

    const onPlus = () => product.amount++;
    const onMinus = () => {
        if (product.amount === 1) {
            onRemove();
        } else {
            product.amount--;
        }
    }
    const onRemove = () => dispatch('removeProduct', product);
</script>

<Row>
    <Column>{product.name}</Column>
    <Column>${product.price}</Column>
    <Column>{product.amount}</Column>
    <Column>
        <div class="buttons">
            <IconButton icon={PlusIcon} size="14" on:click={onPlus} />
            <IconButton icon={MinusIcon} size="14" on:click={onMinus} />
            <IconButton icon={XIcon} size="14" on:click={onRemove} />
        </div>
    </Column>
</Row>

<style lang="scss">
    .buttons {
      display: flex;
    }
</style>
