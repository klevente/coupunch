<script>
    import { createEventDispatcher } from 'svelte';
    import { Card } from 'attractions';
    import { ChevronRightIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let user;

    function onCardClick() {
        dispatch('click', {
            user
        });
    }
</script>

<div class="user-card" on:click={onCardClick}>
    <Card>
        <div>{user.name}</div>
        <div>{user.username}</div>
        <ChevronRightIcon size="24" />
    </Card>
</div>

<style src="../../../../static/css/routes/redeem/_components/user-card.scss"></style>
