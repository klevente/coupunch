<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button } from 'attractions';
    import { XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let coupon;

    function onEdit() {
        dispatch('edit', coupon);
    }

    function onDelete() {
        dispatch('delete', coupon);
    }
</script>

<Card class="coupon-card">
    <p>{coupon.name}</p>
    <Button round neutral on:click={onEdit}>
        <EditIcon size="20" />
    </Button>
    <Button round neutral on:click={onDelete}>
        <XIcon size="20" />
    </Button>
</Card>
