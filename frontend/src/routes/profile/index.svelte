<script>
    import { onMount } from 'svelte';
    import { createForm } from 'frontend-library/util/form';
    import State from 'frontend-library/components/state.svelte';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import { FormField, TextField, Button, Divider, Label, H1 } from 'attractions';
    import * as yup from 'yup';
    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const { state, user } = viewmodel;

    onMount(async () => {
        await viewmodel.get();
    });

    const dataSchema = yup.object({
        email: yup.string().email().required(),
        username: yup.string().required()
    });

    const {
        form: credentialsForm,
        errors: credentialsErrors,
        data: credentialsData,
        setFields: credentialsSetFields,
    } = createForm({
        schema: dataSchema, onSubmit: () => {
            console.log('submit');
        }, onError: () => {
        }
    });

    const passwordSchema = yup.object({
        password: yup.string().required(),
        confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords do not match!').required()
    });

    const { form: passwordForm, errors: passwordErrors, reset: passwordReset } = createForm({
        schema: passwordSchema, onSubmit: () => {
        }, onError: () => {
        }
    });

    $: {
        if ($user.success) {
            const {  username, email } = $user.data;
            credentialsSetFields({ username, email });
            // force an update on the data store
            $credentialsData = $credentialsData;
        }
    }
</script>

<svelte:head>
    <title>Profile</title>
</svelte:head>

<H1>Profile</H1>
<Dynamic data={user}>
    <svelte:fragment slot="data">
        <Divider text="Basic Info"/>
        <form use:credentialsForm>
            <FormField
                    name="Email"
                    help="Your email address"
                    required
                    errors={[$credentialsErrors.email]}
            >
                <TextField name="email"/>
            </FormField>
            <FormField
                    name="Username"
                    help="Your unique username"
                    required
                    errors={[$credentialsErrors.username]}
            >
                <TextField name="username"/>
            </FormField>
            <Button type="submit" filled>Update</Button>
        </form>

        <Divider text="Password"/>

        <form use:passwordForm>
            <FormField
                    name="Password"
                    help="Your password"
                    required
                    errors={[$passwordErrors.password]}
            >
                <TextField name="password" type="password"/>
            </FormField>
            <FormField
                    name="Confirm Password"
                    help="Your password again"
                    required
                    errors={[$passwordErrors.confirmPassword]}
            >
                <TextField name="confirmPassword" type="password"/>
            </FormField>
            <Button type="submit" filled>Update</Button>
        </form>

        <Divider text="QR"/>

        <div class="qr-buttons">
            <Button filled>Update QR</Button>
            <Button filled href="api/users/current/qr/export">Export QR</Button>
        </div>
    </svelte:fragment>
</Dynamic>

<State {state}/>

<style lang="scss">
    .qr-buttons {
      display: flex;
      justify-content: space-around;
    }

</style>
