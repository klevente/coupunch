<script>
    import { FormField, TextField, Button } from 'attractions';
    import State from 'frontend-library/components/state.svelte';
    import { stores } from '@sapper/app';
    import { createForm } from 'frontend-library/util/form';
    import Viewmodel from './_viewmodel';
    import * as yup from 'yup';

    const { session } = stores();

    const viewmodel = new Viewmodel();
    const { state } = viewmodel;

    const schema = yup.object({
        username: yup.string().required(),
        password: yup.string().required(),
    });

    async function onSubmit(request) {
        await viewmodel.login(request, session);
    }

    function onError(errors) {
        console.warn('error in login', errors);
        return {
            username: 'Username or password is incorrect!',
            password: 'Username or password is incorrect!'
        };
    }

    const { form, errors } = createForm({ schema, onSubmit, onError });
</script>

<svelte:head>
    <title>Login</title>
</svelte:head>

<h1>Login</h1>
<form use:form>
    <FormField
            name="Username or Email"
            help="Your Coupunch username or email"
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
    <Button type="submit" outline>Login</Button>
</form>

<State {state}/>

<style lang="scss">

</style>

