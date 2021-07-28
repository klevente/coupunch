<script>
    import { createEventDispatcher } from 'svelte';

    const dispatch = createEventDispatcher();

    export let clickable = false;

    function onClick() {
        if (clickable) {
            dispatch('click');
        }
    }
</script>

<tr on:click={onClick} class:clickable>
    <slot />
</tr>

<style lang="scss">
  @use 'sass:color';
  @use 'theme' as vars;

  .clickable {
    cursor: pointer;
  }

  tr {
    &:nth-child(even) {
      background: color.adjust(vars.$main, $alpha: -0.97);
    }

    &:first-child :global {
      > td:first-child {
        border-top-left-radius: vars.$border-radius;
      }

      > td:last-child {
        border-top-right-radius: vars.$border-radius;
      }
    }

    &:last-child :global {
      > td {
        border-bottom-width: 1px;
      }

      > td:first-child {
        border-bottom-left-radius: vars.$border-radius;
      }

      > td:last-child {
        border-bottom-right-radius: vars.$border-radius;
      }
    }
  }
</style>
