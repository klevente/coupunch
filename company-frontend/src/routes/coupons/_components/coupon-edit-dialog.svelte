<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, FormField, TextField, Button, RadioChipGroup } from 'attractions';
    import { isEditing, createForm } from '../../../util/form';
    import * as yup from 'yup';
    import { generate } from '../../../util/array';

    const dispatch = createEventDispatcher();

    export function open(coupon) {
        modalOpen = true;
        setFields(coupon);
        editing = isEditing(coupon);
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

    const schema = yup.object({
        name: yup.string().required(),
    });

    const { form, data, setFields, reset } = createForm({
        schema, onSubmit, onError,
        initialValues: {
            eligibleItems: {
                products: [],
                productGroups: []
            },
            rewards: []
        }
    });

    const couponTypes = [
        { value: 'point', label: 'Point' },
        { value: 'price', label: 'Price' }
    ];

    const toIndexArray = arr => generate(arr.size, i => i);
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
            <fieldset name="eligibleItems">
                {#each toIndexArray($data.eligibleItems.products) as index}
                    <fieldset name="products[{index}]">
                        a
                    </fieldset>
                {/each}
                {#each toIndexArray($data.eligibleItems.productGroups) as index}
                    <fieldset name="productGroups[{index}]">
                        a
                    </fieldset>
                {/each}
            </fieldset>
            {#each toIndexArray($data.rewards) as index}
                <fieldset name="rewards[{index}]">
                    a
                </fieldset>
            {/each}
            <div class="button-bar">
                <Button on:click={close}>Cancel</Button>
                <Button type="submit">{editing ? 'Update' : 'Add'}</Button>
            </div>
        </form>
    </Dialog>
</Modal>

<style lang="scss">
    fieldset {
      border: none;
      margin: 0;
      padding: 0;
    }
</style>
