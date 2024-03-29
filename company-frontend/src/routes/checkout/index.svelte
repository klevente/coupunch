<script>
    import { onMount } from 'svelte';
    import { goto } from '@sapper/app';
    import { Button, H1, H2 } from 'attractions';
    import SearchField from 'frontend-library/components/search-field.svelte';
    import State from 'frontend-library/components/state.svelte';
    import DynamicTable from 'frontend-library/components/dynamic-table.svelte';
    import CustomerRow from './_components/customer-row.svelte';
    import CustomerAddDialog from './_components/customer-add-dialog.svelte';
    import QrDialog from './_components/qr-dialog.svelte';
    import FoundCustomerDialog from './_components/found-customer-dialog.svelte';

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
    let qrDialog;
    let foundCustomerDialog;

    const navigateToCheckoutPage = ({ username, couponsToRedeem }) => {
        let path = `checkout/${username}`;
        if (!!couponsToRedeem) {
            path += couponsToRedeem;
        }
        goto(path);
    };

    const onCustomerClick = (detail) => navigateToCheckoutPage(detail);
    const onNewCustomerClick = () => customerAddDialog.open();
    const onCustomerSearchTermChange = ({ detail }) => viewmodel.searchCustomers(detail);
    const onAddNewCustomer = ({ detail }) => viewmodel.addToCompany(detail, navigateToCheckoutPage);
    const onQrClick = () => qrDialog.open();
    const onQrSuccess = (result) => {
        qrDialog.close();
        foundCustomerDialog.open(result);
    };
    const onScan = ({ detail }) => viewmodel.validateQrCode(detail, onQrSuccess);
    const onQrUserAccept = ({ detail }) => detail.newlyAdded
        ? viewmodel.addToCompany(detail, navigateToCheckoutPage)
        : navigateToCheckoutPage(detail);
</script>

<svelte:head>
    <title>Checkout</title>
</svelte:head>

<H1>Checkout</H1>
<section>
    <div class="user-header">
        <H2>Choose Customer</H2>
        <SearchField {searchTerm}/>
        <div class="flex-spacer"></div>
        <Button filled on:click={onNewCustomerClick}>Add New Customer</Button>
        <Button filled on:click={onQrClick}>Scan QR</Button>
    </div>

    <DynamicTable data={displayedCustomers} {sortBy} columns={[
        { name: 'Name', property: 'name' },
        { name: 'Username', property: 'username' }
    ]}>
        <CustomerRow
                slot="row" let:row
                customer={row}
                on:click={() => onCustomerClick(row)}
        />
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

    <QrDialog
            bind:this={qrDialog}
            on:scan={onScan}
    />

    <FoundCustomerDialog
            bind:this={foundCustomerDialog}
            on:accept={onQrUserAccept}
    />

    <State {state}/>
</section>


<style lang="scss">
  .user-header {
    display: flex;
    align-items: center;

    :global h2 {
      margin-bottom: 0 !important;
      margin-right: 0.4em;
    }
  }
</style>
