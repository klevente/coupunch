<script>
    import { onMount } from 'svelte';
    import State from 'frontend-library/components/state.svelte';
    import DynamicTable from 'frontend-library/components/dynamic-table.svelte';
    import CompanyRow from './_components/company-row.svelte';
    import Viewmodel from './_viewmodel';
    import { goto } from '@sapper/app';

    const viewmodel = new Viewmodel();
    const { state, sortBy, displayedCompanies } = viewmodel;

    onMount(async () => {
        await viewmodel.getUserCompanies();
    });

    const onCompanyClick = ({ url }) => goto(`companies/${url}`);
</script>

<svelte:head>
    <title>Companies</title>
</svelte:head>

<DynamicTable data={displayedCompanies} {sortBy} columns={[
    { name: 'Name', property: 'name' }
]}>
    <CompanyRow slot="row" let:row company={row} on:click={() => onCompanyClick(row)} />

    <svelte:fragment slot="empty">
        You have not interacted with any companies yet. Once you acquire some points at a place, it will show up here.
    </svelte:fragment>
</DynamicTable>

<State {state}/>

<style lang="scss">

</style>
