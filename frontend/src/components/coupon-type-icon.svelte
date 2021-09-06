<script>
    import { AwardIcon, DollarSignIcon, XCircleIcon } from 'svelte-feather-icons';
    import CouponTypeChoice from './coupon-type-choice.svelte';

    export let type;
</script>

<div class="icon">
    <CouponTypeChoice {type}>
        <AwardIcon slot="point" size="24"/>
        <DollarSignIcon slot="price" size="24"/>
        <XCircleIcon slot="unknown" size="24"/>
    </CouponTypeChoice>
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
