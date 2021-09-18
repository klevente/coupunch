<script>
    import PriceDisplay from '../../../../components/price-display.svelte';
    import AmountDisplay from '../../../../components/amount-display.svelte';

    export let reward;
</script>

<div class="row">
    <div class="left">{reward.name}</div>
    <div class="center">
        <AmountDisplay amount={reward.amount}/>
    </div>
    <div class="right">
        <PriceDisplay price={reward.originalPrice} discountedPrice={reward.discountedPrice}/>
    </div>
</div>

<style lang="scss">
  @use 'theme' as vars;

  .left {
    float: left;
    width: 50%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .center {
    float: left;
    width: 25%;
  }

  .right {
    float: left;
    width: 25%;
    text-align: right;
  }

  .row {
    width: 100%;
    margin-bottom: 10px;
  }

  .row:after {
    content: "";
    display: table;
    clear: both;
  }
</style>
