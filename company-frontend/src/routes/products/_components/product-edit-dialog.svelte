<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, FormField, TextField, Button, RadioChipGroup } from 'attractions';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import { isEditing, createForm } from 'frontend-library/util/form';
    import * as yup from 'yup';

    const dispatch = createEventDispatcher();

    export let productGroups;

    export function open(product) {
        modalOpen = true;
        editing = isEditing(product);
        if (editing) {
            product = {
                ...product,
                group: product.group?.id?.toString() || 'uncategorized'
            };
            console.log(product);
            setFields(product);
        }
    }

    function close() {
        modalOpen = false;
        reset();
    }

    function onSubmit(product) {
        product = {
            ...product,
            group: parseInt(product.group) || null
        }
        dispatch(editing ? 'update' : 'add', product);
        close();
    }

    function onError(errors) {
        console.error(errors);
    }

    let modalOpen = false;
    let editing = false;

    const schema = yup.object({
        name: yup.string().required(),
        price: yup.number().required().min(0.01),
        group: yup.string().required()
    });

    const { form, setFields, reset } = createForm({ schema, onSubmit, onError });
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog
            title={editing ? 'Edit Product' : 'Add Product'}
    >
        <form use:form>
            <input type="hidden" name="id">
            <FormField
                    name="Name"
                    help="Name of the product"
                    required
            >
                <TextField name="name"/>
            </FormField>
            <FormField
                    name="Price"
                    help="Unit price of the product"
                    required
            >
                <TextField name="price" withItem itemRight type="number" min="0.01" step="0.01">
                    <span class="item currency">$</span>
                </TextField>
            </FormField>
            <FormField
                    name="Product Group"
                    help="Product Group the Product is a part of"
                    required
            >
                <Dynamic data={productGroups}>
                    <RadioChipGroup
                            slot="data" let:data
                            items={data} name="group"
                    />
                </Dynamic>
            </FormField>
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
                <Button type="submit">{editing ? 'Update' : 'Add'}</Button>
            </div>
        </form>
    </Dialog>
</Modal>

<style lang="scss">
  @use 'theme' as vars;

  .currency {
    font-weight: 500;
    top: 0.8em !important;
  }
</style>
