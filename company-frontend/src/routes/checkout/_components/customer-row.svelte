<script>
    import { createEventDispatcher } from 'svelte';
    import { Button } from 'attractions';
    import { Row, Column } from 'frontend-library/components/table';

    const dispatch = createEventDispatcher();

    export let customer;

    const onResendClick = (e) => {
        e.stopPropagation();
        dispatch('resend');
    };
</script>

<Row clickable on:click>
    <Column>{customer.name}</Column>
    <Column>{customer.username}</Column>
    <Column>
        <Button small events={[
            {
                name: 'click',
                handler: onResendClick
            }
        ]}>Resend</Button>
    </Column>
</Row>

<style lang="scss">

</style>
