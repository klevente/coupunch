<script>
    import { onMount } from 'svelte';
    import { goto } from '@sapper/app';
    import { Button, H1, H2 } from 'attractions';
    import SearchField from '../../components/search-field.svelte';
    import State from '../../components/state.svelte';
    import { Row, Column } from '../../components/table';
    import DynamicTable from '../../components/dynamic-table.svelte';

    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const { displayedUsers, state, searchTerm, sortBy } = viewmodel;

    onMount(async () => {
        await viewmodel.get();
    });

    const onUserClick = ({ username }) => goto(`redeem/${username}`);
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
        <Button filled>Add New Customer</Button>
    </div>

    <State {state}/>

    <DynamicTable data={displayedUsers} {sortBy} columns={[
        { name: 'Name', property: 'name' },
        { name: 'Username', property: 'username' }
    ]}>
        <svelte:fragment slot="row" let:row>
            <Row clickable on:click={() => onUserClick(row)}>
                <Column>{row.name}</Column>
                <Column>{row.username}</Column>
            </Row>
        </svelte:fragment>
    </DynamicTable>
</section>


<style lang="scss">
  .user-header {
    display: flex;
  }
</style>
