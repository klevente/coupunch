<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button } from 'attractions';
    import { AlertTriangleIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let title;

    let modalOpen = false;
    let extra = null;

    export function open(data) {
        extra = data;
        modalOpen = true;
    }

    function close() {
        modalOpen = false;
    }

    function onNo() {
        dispatch('no', null);
        close();
    }

    function onYes() {
        dispatch('yes', extra);
        close();
    }
</script>

<Modal bind:open={modalOpen}>
    <Dialog {title} danger>
        <div slot="title-icon">
            <AlertTriangleIcon size="25" />
        </div>
        <p><slot /></p>
        <div class="button-bar">
            <Button on:click={onNo}>No</Button>
            <Button on:click={onYes}>Yes</Button>
        </div>
    </Dialog>
</Modal>

<style src="../../static/css/components/confirm-dialog.scss"></style>
