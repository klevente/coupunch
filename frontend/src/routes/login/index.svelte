<script>
    import { stores, goto } from '@sapper/app';
    import { createForm } from 'frontend-library/util/form';
    import { create } from '@beyonk/sapper-httpclient';
    import * as yup from 'yup';

    const { session } = stores();

    const schema = yup.object({
        username: yup.string().required(),
        password: yup.string().required(),
    });

    async function onSubmit(request) {
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
        // goto('home');
        window.location.href = 'home';
    }

    function onError(errors) {
        console.error(errors);
    }


    const { form } = createForm({ schema, onSubmit, onError });
</script>

<svelte:head>
    <title>Login</title>
</svelte:head>

<h1>Login</h1>
<form use:form>
    <label for="username">Username or email:</label>
    <input id="username" name="username">
    <label for="password">Password:</label>
    <input id="password" name="password" type="password">

    <input type="submit" value="Login">
</form>

<style lang="scss">

</style>

