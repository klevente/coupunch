<script>
    import { stores, goto } from '@sapper/app';
    import { createForm } from 'felte';
    import reporter from '@felte/reporter-tippy';
    import { validator } from '@felte/validator-yup';
    import * as yup from 'yup';

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

    async function login({ username, password }) {
        const response = await fetch('/api/users/login', {
           method: 'post',
           headers: {
               'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
           } ,
            body: new URLSearchParams([
                ['username', username],
                ['password', password],
            ])
        });

        if (response.ok) {
            const currentUser = await response.json();
            $session.user = {
                authenticated: true,
                scope: ['user'],
                ...currentUser
            };
            goto('home');
        } else if (response.status === 403) {
            throw new Error("Password is incorrect");
        } else {
            throw new Error(response.statusText);
        }
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

