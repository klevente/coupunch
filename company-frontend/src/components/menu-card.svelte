<script>
    import { goto } from '@sapper/app';
    import { Card } from 'attractions';

    export let icon;
    export let title;
    export let subtitle;
    export let href;
</script>

<div class="menu-card" on:click={() => goto(href)}>
    <Card>
        <div class="icon">
            <svelte:component this={icon} size="24"/>
        </div>
        <div class="title">{title}</div>
        <div class="subtitle">{subtitle}</div>
    </Card>
</div>

<style lang="scss">
  @use 'theme' as vars;

  .menu-card {
    margin-bottom: 1em;
    cursor: pointer;

    :global .card {
      display: grid;
      grid-template-columns: auto 1fr;
      grid-template-rows: auto auto;
      column-gap: 1em;
      transition: 150ms box-shadow;

      &:hover {
        box-shadow: vars.$shadow-raised;
      }
    }

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

    @media only screen and (min-width: 640px) {
      margin-bottom: 2.5em;

      :global .card {
        width: 15em;
      }
    }

    @media only screen and (min-width: 1024px) {
      margin-bottom: 1em;
    }
  }
</style>
