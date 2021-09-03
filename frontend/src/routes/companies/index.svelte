<script>
    import { onMount } from 'svelte';
    import State from 'frontend-library/components/state.svelte';
    import DynamicTable from 'frontend-library/components/dynamic-table.svelte';
    import CompanyRow from './_components/company-row.svelte';
    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const { state, sortBy, displayedCompanies } = viewmodel;

    onMount(async () => {
        await viewmodel.getUserCompanies();
    });
</script>

<svelte:head>
    <title>Companies</title>
</svelte:head>

<DynamicTable data={displayedCompanies} {sortBy} columns={[
    { name: 'Name', property: 'name' }
]}>
    <CompanyRow slot="row" let:row company={row} on:click={() => console.log(row)} />
</DynamicTable>

<State {state}/>

<style lang="scss">

</style>
