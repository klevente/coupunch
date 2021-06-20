<script>
    import { createEventDispatcher } from 'svelte';
    import { Modal, Dialog, TextField, Button } from 'attractions';

    const dispatch = createEventDispatcher();

    export function open() {
        modalOpen = true;
    }

    export function close() {
        modalOpen = false;
    }

    let modalOpen = false;

    function onAdd() {

    }
</script>

<Modal bind:open={modalOpen} let:closeCallback>
    <Dialog
        title="Add New Customer"
        {closeCallback}
    >

        <Button filled>Add</Button>
    </Dialog>
</Modal>

<style src="../../../../static/css/routes/redeem/_components/user-add-dialog.scss"></style>
