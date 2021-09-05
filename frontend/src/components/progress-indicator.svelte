<script>
    import ProgressLine from './progress-line.svelte';

    export let value;
    export let levels;

    const [ max ] = levels.slice(-1);
    const intermediate = levels.slice(0, -1);

    const toPercent = (val, max) => `${(val / max) * 100}%`;
</script>


<div class="progress-container">
    <progress {max} {value}></progress>
    {#each intermediate as val}
        <ProgressLine percent={toPercent(val, max)}/>
    {/each}
</div>

<style lang="scss">
  @use 'theme' as vars;

  .progress-container {
    position: relative;
  }

  progress {
    display: block; /* default: inline-block */
    width: 100%;
    margin: 0 auto;
    padding: 2px;
    background: vars.$background;
    border-radius: vars.$snackbar-radius;
    border: 1px solid vars.$main;
  }

  progress::-moz-progress-bar {
    border-radius: vars.$snackbar-radius;
    background: vars.$success;

  }

  /* webkit */
  @media screen and (-webkit-min-device-pixel-ratio: 0) {
    progress {
      height: 15px;
    }
  }

  progress::-webkit-progress-bar {
    background: transparent;
  }

  progress::-webkit-progress-value {
    border-radius: vars.$snackbar-radius;
    background: vars.$success;
  }

</style>
