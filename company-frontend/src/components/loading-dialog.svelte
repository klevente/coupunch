<script>
    import { Dialog, Modal, Loading } from 'attractions';

    let modalOpen = false;

    export function open() {
        modalOpen = true;
    }

    export function close() {
        modalOpen = false;
    }
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title="Loading">
        <Loading />
    </Dialog>
</Modal>

<style src="../../static/css/components/loading-dialog.scss"></style>
