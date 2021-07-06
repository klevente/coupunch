<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, FormField, TextField, Button } from 'attractions';
    import { createForm } from 'felte';
    import reporter from '@felte/reporter-tippy';
    import { validator } from '@felte/validator-yup';
    import * as yup from 'yup';

    const dispatch = createEventDispatcher();

    export function open(product) {
        modalOpen = true;
        setFields(product);
        editing = product && !!product.id;
    }

    function close() {
        modalOpen = false;
    }

    let modalOpen = false;
    let editing = false;

    const schema = yup.object({
        name: yup.string().required(),
        price: yup.number().required().min(0.01),
    });

    const { form, setFields } = createForm({
        extend: [validator, reporter()],
        validateSchema: schema,
        onSubmit: values => {
            console.log(values);
            if (!!values.id) {
                dispatch('updateProduct', {
                    product: values
                });
            } else {
                dispatch('addProduct', {
                    product: values
                });
            }
            close();
        },
        onError: errors => console.error(errors),
    });
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
                <TextField name="name" />
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
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
                <Button type="submit">{editing ? 'Update' : 'Add'}</Button>
            </div>
        </form>
    </Dialog>
</Modal>

<!--<style src="../../../../static/css/routes/products/_components/product-edit-dialog.scss"></style>-->
<style lang="scss">
    @use 'theme' as vars;

    .currency {
      font-weight: 500;
      top: 0.8em !important;
    }
</style>
