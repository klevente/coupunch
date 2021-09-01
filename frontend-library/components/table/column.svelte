<script>

</script>

<td>
    <slot />
</td>

<style lang="scss">
  @use 'sass:color';
  @use 'theme' as vars;

  td {
    padding: 1em;
    border: 0 solid color.adjust(vars.$main, $alpha: -0.75);
    border-width: 1px 0 0 1px;
    line-height: 1.3em;

    &:last-child {
      border-right-width: 1px;
    }
  }
</style>
