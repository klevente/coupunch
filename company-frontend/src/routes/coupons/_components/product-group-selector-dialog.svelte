<script>
    import { Dialog, Modal, Button } from 'attractions';

    export let productGroups;
    export let searchTerm;
    export let sortBy;

    export function open() {
        modalOpen = true;
    }

    export function close() {
        modalOpen = false;
    }

    let modalOpen = false;
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title="Select Product Group">

    </Dialog>
</Modal>

<style lang="scss">

</style>
