<script>
    import { createEventDispatcher } from 'svelte';
    import { createForm } from 'frontend-library/util/form';
    import { FormField, TextField, Button, } from 'attractions';
    import * as yup from 'yup';

    const dispatch = createEventDispatcher();

    const schema = yup.object({
        password: yup.string().required(),
        confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords do not match!').required()
    });

    function onSubmit(values) {
        dispatch('submit', values);
    }

    function onError(errors) {
        console.error(errors);
    }

    const { form, errors, reset } = createForm({ schema, onSubmit, onError });

    export function resetForm() {
        reset();
    }
</script>

<form use:form>
    <FormField
            name="Password"
            help="Your password"
            required
            errors={[$errors.password]}
    >
        <TextField name="password" type="password"/>
    </FormField>
    <FormField
            name="Confirm Password"
            help="Your password again"
            required
            errors={[$errors.confirmPassword]}
    >
        <TextField name="confirmPassword" type="password"/>
    </FormField>
    <Button type="submit" filled>Update</Button>
</form>

<style lang="scss">

</style>
