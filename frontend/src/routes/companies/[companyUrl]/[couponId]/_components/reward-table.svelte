<script>
    import { Accordion, AccordionSection} from 'attractions';
    import { ChevronDownIcon } from 'svelte-feather-icons';
    import PriceDisplay from '../../../../../components/price-display.svelte';
    import AmountDisplay from '../../../../../components/amount-display.svelte';
    import RewardProductGroupItems from './reward-product-group-items.svelte';
    import { sortedReactiveSimple } from 'frontend-library/viewmodel/transformations';

    export let items;

    $: sortedItems = sortedReactiveSimple({
        array: items,
        sortProperty: 'name'
    });
</script>

<Accordion let:closeOtherPanels>
    {#each sortedItems as reward}
        {#if reward.type === 'product'}
            <div class="row">
                <div class="left">{reward.name}</div>
                <div class="center">
                    <AmountDisplay amount={reward.amount}/>
                </div>
                <div class="right">
                    <PriceDisplay price={reward.originalPrice} discountedPrice={reward.discountedPrice}/>
                </div>
            </div>
        {:else if reward.type === 'group'}
            <AccordionSection on:panel-open={closeOtherPanels} let:toggle>
                <div slot="handle" class="row" on:click={toggle}>
                    <div class="left">{reward.name}</div>
                    <div class="center">
                        <AmountDisplay amount={reward.amount}/>
                    </div>
                    <div class="right">
                        <ChevronDownIcon size="20" class="accordion-chevron"/>
                    </div>
                </div>
                <RewardProductGroupItems products={reward.products}/>
            </AccordionSection>
        {:else}
            <div>Invalid type {reward.type}</div>
        {/if}
    {/each}
</Accordion>

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
  }

  .row:after {
    content: "";
    display: table;
    clear: both;
  }

  :global .accordion {
    section {
      margin: 0.5em 0 0.5em 0;
      padding-left: 0.5em;
      border-left: 1px solid vars.$light-contrast;
      display: flex;
      flex-direction: column;
      align-items: flex-start;

      .left {
        /*width: calc(50% - 0.5em);*/
        width: 50%
      }

      .right {
        width: 50%
      }
    }
  }
</style>
