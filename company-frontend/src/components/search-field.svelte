<script>
    import { TextField } from 'attractions';

    export let placeholder = 'Search...';
    export let searchTerm;
</script>

<TextField outline type="search" {placeholder} bind:value={$searchTerm} />
