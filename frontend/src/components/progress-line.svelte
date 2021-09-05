<script>
    import { onMount } from 'svelte';

    export let percent;

    let element;

    onMount(() => {
        element.style.left = percent;
    });
</script>

<div class="line" bind:this={element}></div>

<style lang="scss">
  @use 'theme' as vars;

  .line {
    position: absolute;
    top: 0;
    left: 0;
    border-left: 1px dashed vars.$main;
    height: 15px;
  }
</style>
