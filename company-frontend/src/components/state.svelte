<script>
    import { SnackbarContainer } from 'attractions';
    import LoadingDialog from './loading-dialog.svelte';
    import { StateStatus } from '../viewmodel/state-store';

    export let state;

    function emitLoading() {
        loadingDialog.open();
    }

    function emitSuccess(text) {
        loadingDialog.close();
        snackbar.showSnackbar({ props: { text } });
    }

    function emitFailure(error) {
        loadingDialog.close();
        snackbar.showSnackbar({ props: { text: `Error: ${error}` } });
    }

    let loadingDialog;
    let snackbar;

    $: {
        console.log($state.info());
        const { status, statusText } = $state;
        switch (status) {
            case StateStatus.LOADING:
                emitLoading();
                break;
            case StateStatus.SUCCESS:
                emitSuccess(statusText);
                break;
            case StateStatus.FAILURE:
                emitFailure(statusText);
                break;
            case StateStatus.INACTIVE:
                break;
            default:
                console.error(`Unknown StateStatus: ${$state.status}`);
        }
    }
</script>

<LoadingDialog bind:this={loadingDialog}/>
<SnackbarContainer bind:this={snackbar}/>
