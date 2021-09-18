<script>
    import { Accordion, AccordionSection, H3 } from 'attractions';
    import { ChevronDownIcon } from 'svelte-feather-icons';
    import CouponTypeChoice from '../../../../../components/coupon-type-choice.svelte';
    import PointDisplay from '../../../../../components/point-display.svelte';
    import PriceDisplay from '../../../../../components/price-display.svelte';
    import { sortedReactiveSimple } from 'frontend-library/viewmodel/transformations';

    export let eligibleItems;
    export let type;

    $: sortedEligibleItems = sortedReactiveSimple({
        array: eligibleItems,
        sortProperty: 'name'
    });
</script>

<H3>Eligible Items</H3>
<CouponTypeChoice {type}>
    <div slot="point">
        <Accordion let:closeOtherPanels>
            {#each sortedEligibleItems as item}
                <div class="eligible-item">
                    {#if item.type === 'product'}
                        <div class="row">
                            <div class="left">{item.name}</div>
                            <div class="center">
                                <PointDisplay points={item.points}/>
                            </div>
                            <div class="right">
                                <PriceDisplay price={item.price}/>
                            </div>
                        </div>
                    {:else if item.type === 'group'}
                        <AccordionSection on:panel-open={closeOtherPanels} let:toggle>
                            <div slot="handle" class="row" on:click={toggle}>
                                <div class="left">{item.name}</div>
                                <div class="center">
                                    <PointDisplay points={item.points}/>
                                </div>
                                <div class="right">
                                    <ChevronDownIcon size="20" class="accordion-chevron"/>
                                </div>
                            </div>
                            {#each item.products as product}
                                <div class="row">
                                    <div class="left">{product.name}</div>
                                    <!--<div class="center">{item.points} points</div>-->
                                    <div class="right">
                                        <PriceDisplay price={product.price}/>
                                    </div>
                                </div>
                            {/each}
                        </AccordionSection>
                    {:else}
                        <div>Invalid type {item.type}</div>
                    {/if}
                </div>
            {/each}
        </Accordion>
    </div>
    <div slot="price">
        {#each sortedEligibleItems as item}
            <div class="eligible-item">
                {#if item.type === 'product'}
                    <div class="row">
                        <div class="left50">{item.name}</div>
                        <div class="right50">
                            <PriceDisplay price={item.price}/>
                        </div>
                    </div>
                {:else if item.type === 'group'}
                    <AccordionSection on:panel-open={closeOtherPanels} let:toggle>
                        <div slot="handle" class="row" on:click={toggle}>
                            <div class="left50">{item.name}</div>
                            <div class="right50">
                                <ChevronDownIcon size="20" class="accordion-chevron"/>
                            </div>
                        </div>
                        {#each item.products as product}
                            <div class="row">
                                <div class="left">{product.name}</div>
                                <div class="right">
                                    <PriceDisplay price={product.price}/>
                                </div>
                            </div>
                        {/each}
                    </AccordionSection>
                {:else}
                    <div>Invalid type {item.type}</div>
                {/if}
            </div>
        {/each}
    </div>
</CouponTypeChoice>

<style lang="scss">
  @use 'theme' as vars;

  .eligible-item {
    margin-bottom: 0.5em;
  }

  .left50 {
    float: left;
    width: 50%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .right50 {
    float: left;
    width: 50%;
    text-align: right;
  }

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
