<script>
    import { Button } from 'attractions';

    export let icon;
    export let size = '20';
</script>

<Button round neutral on:click>
    <svelte:component this={icon} {size}/>
</Button>

<style lang="scss">

</style>
