<script>
    import { onMount } from 'svelte';
    import { goto, stores } from '@sapper/app';
    import { Button, Loading, TextField, H1, H2 } from 'attractions';
    import UserCard from './_components/user-card.svelte';
    import UserAddDialog from './_components/user-add-dialog.svelte';
    import userService from '../../services/user-service';

    import { writable } from 'svelte/store';

    const { session } = stores();
    const { companyUrl, companyName } = $session.user;

    const users = userService.users;
    const userSearchTerm = writable('');
    let userAddDialog;

    onMount(async () => {
        await userService.fetch(companyUrl);
    });

    function onUserClick({ username }) {
        goto(`redeem/${username}`)
    }

    function openUserAddDialog() {
        userAddDialog.open();
    }

</script>

<svelte:head>
    <title>Redeem :: {companyName}</title>
</svelte:head>

<header>
    <H1>Redeem Coupon</H1>
</header>
<article>
    {#if $users.isLoading()}
        <Loading />
    {/if}
    {#if $users.hasData()}
        <div class="user-selector">
            <div class="user-header">
                <H2 class="flex-mr">Choose Customer</H2>
                <TextField outline type="search" bind:value={$userSearchTerm} placeholder="Search..."/>
                <div class="flex-spacer"></div>
                <Button filled on:click={openUserAddDialog}>Add new customer</Button>
            </div>
            <div class="user-list">
                {#each $users.data as user}
                    <UserCard on:click={() => onUserClick(user)} {user}/>
                {/each}
            </div>

            <UserAddDialog bind:this={userAddDialog} />
        </div>
    {/if}
    {#if $users.isError()}
        <div>Error</div>
    {/if}
</article>

<style lang="scss">
  @use 'theme' as vars;

  .user-header {
    display: flex;
    align-items: center;
  }

  :global h2 {
    margin-bottom: 0 !important;
  }

</style>
