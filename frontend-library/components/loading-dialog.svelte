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
    <Dialog>
        <Loading />
    </Dialog>
</Modal>

<style lang="scss"></style>
