<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, FormField, TextField, Button } from 'attractions';
    import { isEditing, createForm } from 'frontend-library/util/form';
    import * as yup from 'yup';

    const dispatch = createEventDispatcher();

    export function open(productGroup) {
        modalOpen = true;
        setFields(productGroup);
        editing = isEditing(productGroup);
    }

    function close() {
        modalOpen = false;
    }

    function onSubmit(productGroup) {
        dispatch(editing ? 'update' : 'add', productGroup);
        close();
    }

    function onError(errors) {
        console.error(errors);
    }

    let modalOpen = false;
    let editing = false;

    const schema = yup.object({
        name: yup.string().required(),
    });

    const { form, errors, setFields } = createForm({ schema, onSubmit, onError });
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
                    errors={[$errors.name]}
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

<style lang="scss"></style>
