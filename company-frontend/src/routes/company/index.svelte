<script>
    import { Button, Divider, H1 } from 'attractions';
    import { stores } from '@sapper/app';
    import State from 'frontend-library/components/state.svelte';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import SettingsForm from './_components/settings-form.svelte';
    import Viewmodel from './_viewmodel';
    import { fetchAndUpdateCurrentUser } from '../../services/current-user';

    const { session } = stores();

    const viewmodel = new Viewmodel();
    const { settings, state } = viewmodel;

    let settingsForm;

    const onSettingsSubmit = ({ detail }) => {
        viewmodel.update(
            detail,
            () => fetchAndUpdateCurrentUser(session),
            () => settingsForm.updateFields($settings.data)
        );
    }

    const onResendClick = () => viewmodel.resendSettings();
</script>

<svelte:head>
    <title>Company Settings</title>
</svelte:head>

<H1>Settings</H1>
<Dynamic data={settings}>
    <svelte:fragment slot="data" let:data>
        <SettingsForm
                bind:this={settingsForm}
                on:submit={onSettingsSubmit}
                {data}
        />

        <Divider text="Advanced"/>

        <div class="other-buttons">
            <Button filled on:click={onResendClick}>Resend Info</Button>
        </div>
    </svelte:fragment>
</Dynamic>

<State {state}/>

<style lang="scss">
  .other-buttons {
    display: flex;
    justify-content: center;
  }
</style>
