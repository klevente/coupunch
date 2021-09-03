<script>
    import { stores, goto } from '@sapper/app';
    import { FormField, TextField, Button } from 'attractions';
    import State from 'frontend-library/components/state.svelte';
    import { createForm } from 'frontend-library/util/form';
    import * as yup from 'yup';
    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const { state } = viewmodel;

    const { session } = stores();

    const schema = yup.object({
        company: yup.string().required(),
        username: yup.string().required(),
        password: yup.string().required(),
    });

    async function onSubmit(request) {
        await viewmodel.login(request, session);
    }

    function onError(errors) {
        console.warn('error in login', errors);
        return {
            company: '',
            username: 'Username or password is incorrect!',
            password: 'Username or password is incorrect!'
        };
    }

    const { form, errors } = createForm({ schema, onSubmit, onError });
</script>

<svelte:head>
    <title>Company Login</title>
</svelte:head>

<h1>Company Login</h1>
<form use:form>
    <FormField
        name="Company"
        help="The name of the company"
        required
        errors={[$errors.company]}
    >
        <TextField name="company" />
    </FormField>
    <FormField
            name="Username"
            help="Your username in the company"
            required
            errors={[$errors.username]}
    >
        <TextField name="username" />
    </FormField>
    <FormField
            name="Password"
            help="Your password"
            required
            errors={[$errors.password]}
    >
        <TextField name="password" type="password" />
    </FormField>
    <Button type="submit" outline>Login</Button>
</form>

<State {state}/>

<style>

</style>

