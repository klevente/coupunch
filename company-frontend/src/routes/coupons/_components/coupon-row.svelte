<script>
    import { createEventDispatcher } from 'svelte';
    import { XIcon, EditIcon, AwardIcon, DollarSignIcon } from 'svelte-feather-icons';
    import IconButton from '../../../components/icon-button.svelte';
    import { Row, Column } from '../../../components/table';

    const dispatch = createEventDispatcher();

    export let coupon;

    const onEditClick = () => dispatch('edit', coupon);
    const onDeleteClick = () => dispatch('delete', coupon);

    $: console.log(coupon);
</script>

<Row>
    <Column>{coupon.name}</Column>
    <Column>
        {#if coupon.type === 'POINT'}
            <AwardIcon size="24"/>
        {:else if coupon.type === 'PRICE'}
            <DollarSignIcon size="24"/>
        {:else}
            Unknown coupon type!
        {/if}
    </Column>
    <Column>{coupon.eligibleItems.products.length + coupon.eligibleItems.productGroups.length}</Column>
    <Column>{coupon.rewards.length}</Column>
    <Column>
        <div class="button-bar">
            <IconButton icon={EditIcon} on:click={onEditClick}/>
            <IconButton icon={XIcon} on:click={onDeleteClick}/>
        </div>
    </Column>
</Row>

<style lang="scss">

</style>
