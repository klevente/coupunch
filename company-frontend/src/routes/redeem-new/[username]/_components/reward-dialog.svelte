<script>
    import { Dialog, Modal, Button } from 'attractions';

    export let coupon;

    let modalOpen = false;
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title={`Choose Reward for ${coupon.name}`}>

    </Dialog>
</Modal>

<style lang="scss">

</style>
