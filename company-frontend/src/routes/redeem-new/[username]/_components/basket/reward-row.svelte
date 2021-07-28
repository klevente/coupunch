<script>
    import { createEventDispatcher } from 'svelte';
    import { XIcon } from 'svelte-feather-icons';
    import { Row, Column } from '../../../../../components/table';
    import IconButton from '../../../../../components/icon-button.svelte';

    const dispatch = createEventDispatcher();

    export let reward;

    const onRemove = () => dispatch('removeReward', reward);
</script>

<Row>
    <Column>{reward.name}</Column>
    <Column>price todo</Column>
    <Column>{reward.amount}</Column>
    <Column>
        <IconButton icon={XIcon} size="14" on:click={onRemove} />
    </Column>
</Row>

<style lang="scss">

</style>
