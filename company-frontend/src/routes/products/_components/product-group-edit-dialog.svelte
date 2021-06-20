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
    });

    const { form, setFields } = createForm({
        extend: [validator, reporter()],
        validateSchema: schema,
        onSubmit: values => {
            console.log(values);
            if (!!values.id) {
                console.log('update');
            } else {
                console.log('add');
            }
            close();
        },
        onError: errors => console.error(errors),
    });
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog
            title={editing ? 'Edit Product Group' : 'Add Product Group'}
    >
        <form use:form>
            <input type="hidden" name="id">
            <FormField
                    name="Name"
                    help="Name of the product group"
                    required
            >
                <TextField name="name" />
            </FormField>
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
                <Button type="submit">{editing ? 'Update' : 'Add'}</Button>
            </div>
        </form>
    </Dialog>
</Modal>

<style src="../../../../static/css/routes/products/_components/product-group-edit-dialog.scss"></style>
