<script>
    import { H3, Accordion, AccordionSection, Button, Dot } from 'attractions';
    import { ChevronDownIcon } from 'svelte-feather-icons';
    import CouponProgress from '../../../../../components/coupon-progress.svelte';
    import DiscountDisplay from '../../../../../components/discount-display.svelte';
    import RewardTable from './reward-table.svelte';
    import RewardProductGroupProductsDialog from './reward-product-group-products-dialog.svelte';
    import { toIndexArray } from 'frontend-library/util/form';

    export let rewards;
    export let type;
    export let redeemLevel;
    const indices = toIndexArray(rewards);

    let productsDialog;

    const onGroupClick = ({ detail }) => productsDialog.open(detail);
</script>

<H3>Rewards</H3>
<Accordion let:closeOtherPanels>
    {#each indices as index}
        <AccordionSection on:panel-open={closeOtherPanels} let:toggle>
            <div slot="handle">
                <Button on:click={toggle}>
                    {#if redeemLevel === index}
                        <Dot success class="mr"/>
                    {/if}
                    Level {index + 1} at
                    <CouponProgress {type} progress={rewards[index].threshold}/>,
                    <DiscountDisplay
                            discountType={rewards[index].discountType}
                            discount={rewards[index].discount}/>
                    <ChevronDownIcon size="20" class="ml accordion-chevron"/>
                </Button>
            </div>
            <RewardTable
                    reward={rewards[index]}
                    on:group-click={onGroupClick}
            />
        </AccordionSection>
    {/each}
</Accordion>

<RewardProductGroupProductsDialog bind:this={productsDialog}/>

<style lang="scss">

</style>
