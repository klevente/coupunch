<script>
    import { AwardIcon, DollarSignIcon, XCircleIcon } from 'svelte-feather-icons';

    export let type;

    const typeLowercase = type.toLowerCase();
</script>

<div class="icon">
    {#if type === 'point'}
        <AwardIcon size="24"/>
    {:else if type === 'price'}
        <DollarSignIcon size="24"/>
    {:else}
        <XCircleIcon size="24"/>
    {/if}
</div>

<style lang="scss">
  @use 'theme' as vars;

  .icon {
    padding: 0.8em;
    border-radius: 50%;
    background: vars.$main;
    color: white;
    grid-row: span 2;
    align-self: center;

    :global svg {
      display: block;
    }
  }
</style>
