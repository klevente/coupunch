<script>
    import { createEventDispatcher } from 'svelte';
    import { createForm } from 'frontend-library/util/form';
    import { FormField, TextField, Button, } from 'attractions';
    import * as yup from 'yup';

    export let data;
    const { username, email, name } = data;

    const dispatch = createEventDispatcher();

    const schema = yup.object({
        email: yup.string().email().required(),
        username: yup.string().required()
    });

    function onSubmit(values) {
        dispatch('submit', values);
    }

    function onError(errors) {
        console.error(errors);
    }

    const { form, errors, setFields } = createForm({
        schema, onSubmit, onError,
        initialValues: { username, email, name }
    });

    export function updateFields(credentials) {
        setFields(credentials);
    }
</script>

<form use:form>
    <FormField
            name="Email"
            help="Your email address"
            required
            errors={[$errors.email]}
    >
        <TextField name="email"/>
    </FormField>
    <FormField
            name="Username"
            help="Your unique username"
            required
            errors={[$errors.username]}
    >
        <TextField name="username"/>
    </FormField>
    <FormField
            name="Name"
            help="Your display name"
            required
            errors={[$errors.name]}
    >
        <TextField name="name"/>
    </FormField>
    <Button type="submit" filled>Update</Button>
</form>

<style lang="scss">

</style>
