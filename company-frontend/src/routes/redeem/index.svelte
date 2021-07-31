<script>
    import { onMount } from 'svelte';
    import { goto } from '@sapper/app';
    import { Button, H1, H2 } from 'attractions';
    import SearchField from '../../components/search-field.svelte';
    import State from '../../components/state.svelte';
    import DynamicTable from '../../components/dynamic-table.svelte';
    import CustomerRow from './_components/customer-row.svelte';
    import CustomerAddDialog from './_components/customer-add-dialog.svelte';

    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const {
        displayedCustomers,
        state,
        searchTerm,
        sortBy,
        displayedFoundCustomers,
        foundCustomersSortBy
    } = viewmodel;

    onMount(async () => {
        await viewmodel.get();
    });

    let customerAddDialog;

    const onCustomerClick = ({ username }) => goto(`redeem/${username}`);
    const onNewCustomerClick = () => customerAddDialog.open();
    const onCustomerSearchTermChange = ({ detail }) => viewmodel.searchCustomers(detail);
    const onAddNewCustomer = ({ detail }) => viewmodel.addToCompany(detail, onCustomerClick);
</script>

<svelte:head>
    <title>Redeem</title>
</svelte:head>

<H1>Select User</H1>
<section>
    <div class="user-header">
        <H2>Choose Customer</H2>
        <SearchField {searchTerm}/>
        <div class="flex-spacer"></div>
        <Button filled on:click={onNewCustomerClick}>Add New Customer</Button>
    </div>

    <State {state}/>

    <DynamicTable data={displayedCustomers} {sortBy} columns={[
        { name: 'Name', property: 'name' },
        { name: 'Username', property: 'username' }
    ]}>
        <svelte:fragment slot="row" let:row>
            <CustomerRow customer={row} on:click={() => onCustomerClick(row)}/>
        </svelte:fragment>
        <svelte:fragment slot="empty">
            No users match the selected search query.
        </svelte:fragment>
    </DynamicTable>

    <CustomerAddDialog
            bind:this={customerAddDialog}
            foundCustomers={displayedFoundCustomers}
            sortBy={foundCustomersSortBy}
            on:searchTermChange={onCustomerSearchTermChange}
            on:addCustomer={onAddNewCustomer}
    />
</section>


<style lang="scss">
  .user-header {
    display: flex;
  }
</style>
