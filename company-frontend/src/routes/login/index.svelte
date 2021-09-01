<script>
    import { stores, goto } from '@sapper/app';
    import { FormField, TextField, Button } from 'attractions';
    import { createForm } from 'felte';
    import reporter from '@felte/reporter-tippy';
    import { validator } from '@felte/validator-yup';
    import { create } from '@beyonk/sapper-httpclient';
    import * as yup from 'yup';

    const { session } = stores();

    const schema = yup.object({
        company: yup.string().required(),
        username: yup.string().required(),
        password: yup.string().required(),
    });

    const { form } = createForm({
        extend: [validator, reporter()],
        validateSchema: schema,
        onSubmit: values => {
            console.log(values);
            login(values);
        },
        onError: errors => console.error(errors),
    });

    async function login(request) {
        const { company } = request;
        const api = create();
        const currentUser = await api
            .endpoint(`${company}/users/login`)
            .formEncoded()
            .payload(request)
            .forbidden((e) => {
                throw new Error("Password is incorrect");
            })
            .default(e => {
                throw new Error(e);
            })
            .post();

        $session.user = {
            authenticated: true,
            scope: ['company'],
            ...currentUser
        };
        // goto('home');
        window.location.href = 'home';
    }
</script>

<svelte:head>
    <title>Company Login</title>
</svelte:head>

<h1>Company Login</h1>
<form use:form>
    <!--<label for="company">Company:</label>
    <input id="company" name="company">
    <label for="username">Username or email:</label>
    <input id="username" name="username">
    <label for="password">Password:</label>
    <input id="password" name="password" type="password">

    <input type="submit" value="Login">-->
    <FormField
        name="Company"
        help="The name of the company"
        required
    >
        <TextField name="company" />
    </FormField>
    <FormField
            name="Username"
            help="Your username in the company"
            required
    >
        <TextField name="username" />
    </FormField>
    <FormField
            name="Password"
            help="Your password"
            required
    >
        <TextField name="password" type="password" />
    </FormField>
    <Button type="submit" outline>Login</Button>
</form>

<style>

</style>

