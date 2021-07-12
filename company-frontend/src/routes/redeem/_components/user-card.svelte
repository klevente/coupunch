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

<style lang="scss">
  @use 'theme' as vars;

  .user-card {

    width: 100%;

    margin-bottom: 1em;
    cursor: pointer;

    :global .card {
      transition: 150ms box-shadow;
      &:hover {
        box-shadow: vars.$shadow-raised;
      }

      width: 100% !important;
      padding: 0.5em;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
  }
</style>
