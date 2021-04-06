<script>
    import { stores, goto } from '@sapper/app';
    import { createForm } from 'felte';
    import reporter from '@felte/reporter-tippy';
    import { validator } from '@felte/validator-yup';
    import { create } from '@beyonk/sapper-httpclient';
    import * as yup from 'yup';
    import { dataTypes } from "../../util/data-types";

    const { session } = stores();

    const schema = yup.object({
        username: yup.string().required(),
        password: yup.string().required(),
    });

    const { form } = createForm({
        extend: [validator, reporter],
        validateSchema: schema,
        onSubmit: values => {
            console.log(values);
            login(values);
        },
        onError: errors => console.error(errors),
    });

    async function login(request) {
        const api = create();

        const currentUser = await api
            .endpoint('users/login')
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
            scope: ['user'],
            ...currentUser
        };
        goto('home');
    }
</script>

<h1>Login</h1>
<form use:form>
    <label for="username">Username or email:</label>
    <input id="username" name="username">
    <label for="password">Password:</label>
    <input id="password" name="password" type="password">

    <input type="submit" value="Login">
</form>

<style>

</style>

