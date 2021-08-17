<script>
    import { createEventDispatcher } from 'svelte';
    import { Dialog, Modal, Button, TextField } from 'attractions';
    import DynamicTable from '../../../components/dynamic-table.svelte';
    import CustomerRow from './customer-row.svelte';

    const dispatch = createEventDispatcher();

    export let foundCustomers;
    export let sortBy;

    export function open() {
        modalOpen = true;
    }

    export function close() {
        modalOpen = false;
    }

    let modalOpen = false;
    let noSearchTerm = true;

    const onSearchInput = ({ detail: { value } }) => {
        noSearchTerm = value.trim() === ''
        dispatch('searchTermChange', value);
    };
    const onCustomerClick = (customer) => dispatch('addCustomer', customer);
</script>

<Modal bind:open={modalOpen} noClickaway>
    <Dialog title="Add New Customer">
        <TextField placeholder="Search..." on:input={onSearchInput}/>
        {#if noSearchTerm}
            <div>Start searching to get some results...</div>
        {:else}
            <DynamicTable data={foundCustomers} {sortBy} columns={[
            { name: 'Name', property: 'name' },
            { name: 'Username', property: 'username' }
        ]}>
                <svelte:fragment slot="row" let:row>
                    <CustomerRow customer={row} on:click={() => onCustomerClick(row)}/>
                </svelte:fragment>
                <svelte:fragment slot="empty">
                    No data can be found using this query.
                </svelte:fragment>
            </DynamicTable>
        {/if}
        <div class="button-bar">
            <Button on:click={close}>Cancel</Button>
        </div>
    </Dialog>
</Modal>

<style lang="scss">

</style>
