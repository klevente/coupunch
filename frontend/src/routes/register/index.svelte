<script>
    import { FormField, TextField, Button } from 'attractions';
    import State from 'frontend-library/components/state.svelte';
    import { createForm } from 'frontend-library/util/form';
    import * as yup from 'yup';
    import { goto } from '@sapper/app';
    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const { state } = viewmodel;

    const schema = yup.object({
        email: yup.string().email().required(),
        username: yup.string().required(),
        password: yup.string().required(),
    });

    async function onSubmit(request) {
        console.log(request);
        await viewmodel.register(request, goto);
    }

    function onError(errors) {
        console.error(errors);
    }

    const { form, errors } = createForm({ schema, onSubmit, onError });

</script>

<svelte:head>
    <title>Register</title>
</svelte:head>

<h1>Register</h1>
<form use:form>
    <FormField
            name="Email"
            help="Your email"
            required
            errors={[$errors.email]}
    >
        <TextField name="email"/>
    </FormField>
    <FormField
            name="Username"
            help="Your chosen username"
            required
            errors={[$errors.username]}
    >
        <TextField name="username"/>
    </FormField>
    <FormField
            name="Password"
            help="Your password"
            required
            errors="{[$errors.password]}"
    >
        <TextField name="password" type="password"/>
    </FormField>
    <Button type="submit" outline>Create Account</Button>
</form>

<State {state}/>

<style lang="scss">

</style>

