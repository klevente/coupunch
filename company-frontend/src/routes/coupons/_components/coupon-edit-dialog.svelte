<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, FormField, TextField, Button } from 'attractions';
    import { isEditing, createForm } from '../../../util/form';
    import * as yup from 'yup';

    const dispatch = createEventDispatcher();

    export function open(coupon) {
        modalOpen = true;
        setFields(coupon);
        editing = isEditing(coupon);
    }

    function close() {
        modalOpen = false;
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

    const schema = yup.object({
        name: yup.string().required(),
    });

    const { form, setFields } = createForm({ schema, onSubmit, onError });
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
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
                <Button type="submit">{editing ? 'Update' : 'Add'}</Button>
            </div>
        </form>
    </Dialog>
</Modal>
