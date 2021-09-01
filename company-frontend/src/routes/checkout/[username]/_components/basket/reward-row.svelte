<script>
    import { createEventDispatcher } from 'svelte';
    import { XIcon } from 'svelte-feather-icons';
    import { Row, Column } from 'frontend-library/components/table';
    import IconButton from 'frontend-library/components/icon-button.svelte';

    const dispatch = createEventDispatcher();

    export let reward;

    const onRemove = () => dispatch('removeReward', reward);
</script>

<Row>
    <Column>{reward.name}</Column>
    <Column><s>${reward.originalPrice}</s> - ${reward.discountedPrice}</Column>
    <Column>{reward.amount}</Column>
    <Column>
        <IconButton icon={XIcon} size="14" on:click={onRemove} />
    </Column>
</Row>

<style lang="scss">

</style>
