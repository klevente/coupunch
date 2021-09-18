<script>
    import { createEventDispatcher } from 'svelte';
    import { createForm } from 'frontend-library/util/form';
    import { FormField, TextField, Button, } from 'attractions';
    import * as yup from 'yup';

    export let data;
    const { name } = data;

    const dispatch = createEventDispatcher();

    const schema = yup.object({
        name: yup.string().required()
    });

    function onSubmit(values) {
        dispatch('submit', values);
    }

    function onError(errors) {
        console.error(errors);
    }

    const { form, errors, setFields } = createForm({
        schema, onSubmit, onError,
        initialValues: { name }
    });

    export function updateFields(settings) {
        setFields(settings);
    }
</script>

<form use:form>
    <FormField
            name="Name"
            help="The company's name"
            required
            errors={[$errors.name]}
    >
        <TextField name="name"/>
    </FormField>
    <Button type="submit" filled>Update</Button>
</form>

<style lang="scss">

</style>
