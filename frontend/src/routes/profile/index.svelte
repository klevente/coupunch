<script>
    import { onMount } from 'svelte';
    import { stores } from '@sapper/app';
    import { fetchAndUpdateCurrentUser } from '../../services/current-user';
    import State from 'frontend-library/components/state.svelte';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import { Button, Divider, H1 } from 'attractions';
    import CredentialsForm from './_components/credentials-form.svelte';
    import PasswordForm from './_components/password-form.svelte';
    import Viewmodel from './_viewmodel';

    const { session } = stores();

    const viewmodel = new Viewmodel();
    const { state, user } = viewmodel;

    $: console.log($user);

    let credentialsForm;
    let passwordForm;

    onMount(async () => {
        await viewmodel.get();
    });

    const onCredentialsSubmit = ({ detail }) => {
        viewmodel.updateCredentials(
            detail,
            () => fetchAndUpdateCurrentUser(session),
            () => credentialsForm.updateFields($user.data)
        );
    };
    const onPasswordSubmit = ({ detail }) => {
        viewmodel.updatePassword(detail.password, passwordForm.resetForm);
    };
    const onUpdateQrClick = () => viewmodel.updateQr();

</script>

<svelte:head>
    <title>Profile</title>
</svelte:head>

<H1>Profile</H1>
<Dynamic data={user}>
    <svelte:fragment slot="data" let:data>
        <Divider text="Basic Info"/>

        <CredentialsForm
                bind:this={credentialsForm}
                on:submit={onCredentialsSubmit}
                {data}
        />

        <Divider text="Password"/>

        <PasswordForm
                bind:this={passwordForm}
                on:submit={onPasswordSubmit}
        />

        <Divider text="QR"/>

        <div class="qr-buttons">
            <Button filled on:click={onUpdateQrClick}>Update QR</Button>
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
