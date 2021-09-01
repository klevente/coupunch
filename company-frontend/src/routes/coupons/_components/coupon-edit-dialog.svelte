<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, FormField, TextField, Button, RadioChipGroup, Label, Autocomplete } from 'attractions';
    import { PlusIcon, XIcon } from 'svelte-feather-icons';
    import { ArrayField } from 'frontend-library/components/form';
    import IconButton from 'frontend-library/components/icon-button.svelte';
    import ProductSelectorDialog from './product-selector-dialog.svelte';
    import ProductGroupSelectorDialog from './product-group-selector-dialog.svelte';

    import { isEditing, createForm } from '../../../util/form';
    import * as yup from 'yup';

    const dispatch = createEventDispatcher();

    export let products;
    export let productGroups;
    export let productSearchTerm;
    export let productGroupSearchTerm;
    export let productSortBy;
    export let productGroupSortBy;

    export function open(coupon) {
        modalOpen = true;
        editing = isEditing(coupon);
        if (editing) {
            setFields(coupon);
        }
    }

    function close() {
        modalOpen = false;
        reset();
    }

    function onSubmit(coupon) {
        dispatch(editing ? 'update' : 'add', coupon);
        close();
    }

    function onError(errors) {
        console.error(errors);
    }

    let modalOpen = false;
    let editing = false;
    let productSelectorDialog, productGroupSelectorDialog;

    const schema = yup.object({
        name: yup.string().required(),
    });

    const { form, data, setFields, reset } = createForm({
        schema, onSubmit, onError,
        initialValues: {
            eligibleItems: {
                products: [
                    // { id: 1, name: 'test' }
                ],
                productGroups: []
            },
            rewards: []
        }
    });

    const couponTypes = [
        { value: 'point', label: 'Point' },
        { value: 'price', label: 'Price' }
    ];

    const onProductAddClick = () => {
        console.log('click');
        $data.eligibleItems.products.push({ id: 1, name: 'test' });
    };
    const onProductGroupAddClick = () => {};
    const onRewardAddClick = () => {};

    $: console.log($data);
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title={editing ? 'Edit Coupon' : 'Add Coupon'}>
        <form use:form>
            <input type="hidden" name="id">
            <FormField
                    name="Name"
                    help="Name of the coupon"
                    required
            >
                <TextField name="name"/>
            </FormField>
            <FormField
                    name="Type"
                    help="Type of the coupon"
                    required
            >
                <RadioChipGroup items={couponTypes} name="type"/>
            </FormField>
            <Label>Eligible Items</Label>
            <fieldset name="eligibleItems">
                <div class="eligible-products-container">
                    <div class="item-header">
                        <Label small>Products</Label>
                        <IconButton icon={PlusIcon} size="12" on:click={onProductAddClick}/>
                    </div>
                    <ArrayField data={$data.eligibleItems?.products}>
                        <div slot="empty">No eligible products. Add one with the + icon above.</div>
                        <fieldset slot="row" let:index name="products[{index}]">
                            <input type="number" name="id">
                            <TextField name="name"/>
                        </fieldset>
                    </ArrayField>
                </div>
                <div class="eligible-product-group-container">
                    <div class="item-header">
                        <Label small>Product Groups</Label>
                        <IconButton icon={PlusIcon} size="12"/>
                    </div>
                    <ArrayField data={$data.eligibleItems?.productGroups}>
                        <div slot="empty">No eligible product groups. Add one with the + icon above.</div>
                        <fieldset slot="row" let:index name="productGroups[{index}]">
                            a
                        </fieldset>
                    </ArrayField>
                </div>
            </fieldset>
            <div class="item-header">
                <Label>Rewards</Label>
                <IconButton icon={PlusIcon} size="12"/>
            </div>
            <ArrayField data={$data.rewards}>
                <div slot="empty">No rewards specified. Add one with the + icon above.</div>
                <svelte:fragment slot="row" let:index>
                    <Label small>Reward {index + 1}</Label>
                    <fieldset name="rewards[{index}]">
                        a
                    </fieldset>
                </svelte:fragment>
            </ArrayField>
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
                <Button type="submit">{editing ? 'Update' : 'Add'}</Button>
            </div>
        </form>
    </Dialog>
</Modal>

<ProductSelectorDialog
        bind:this={productSelectorDialog}
        {products}
        searchTerm={productSearchTerm}
        sortBy={productSortBy}
/>

<ProductGroupSelectorDialog
        bind:this={productGroupSelectorDialog}
        {productGroups}
        searchTerm={productGroupSearchTerm}
        sortBy={productGroupSortBy}
/>

<style lang="scss">
  fieldset {
    border: none;
    margin: 0;
    padding: 0;
  }

  .item-header {
    display: flex;
    align-items: center;
  }
</style>
