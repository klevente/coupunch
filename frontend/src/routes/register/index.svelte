<script>
    import { createForm } from 'felte';
    import reporter from '@felte/reporter-tippy';
    import { validator } from '@felte/validator-yup';
    import * as yup from 'yup';
    import { create } from '@beyonk/sapper-httpclient';
    import { goto } from '@sapper/app';

    const schema = yup.object({
        email: yup.string().email().required(),
        username: yup.string().required(),
        password: yup.string().required(),
    });

    const { form } = createForm({
        extend: [validator, reporter],
        validateSchema: schema,
        onSubmit: async values => {
            console.log(values);
            const api = create();


            await api
                .endpoint('users')
                .payload(values)
                .default(e => {
                    console.error(e);
                })
                .post();

            goto('login');
        },
    });
</script>

<svelte:head>
    <title>Register</title>
</svelte:head>

<h1>Register</h1>
<form use:form>
    <label for="email">Email:</label>
    <input id="email" name="email" type="email">
    <label for="username">Username:</label>
    <input id="username" name="username">
    <label for="password">Password:</label>
    <input id="password" name="password" type="password">

    <input type="submit" value="Create Account">
</form>

<style>

</style>

