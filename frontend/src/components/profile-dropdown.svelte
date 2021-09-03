<script>
    import { Dropdown, DropdownShell} from 'attractions';
    import { UserIcon } from 'svelte-feather-icons';
    import IconButton from 'frontend-library/components/icon-button.svelte';

    export let segment;
</script>

<DropdownShell let:toggle>
    <IconButton icon={UserIcon} on:click={toggle} />
    <Dropdown right>
        <a aria-current="{segment === 'profile' ? 'page' : undefined}" href="profile" on:click={toggle}>profile</a>
        <a aria-current="{segment === 'logout' ? 'page' : undefined}" href="logout" on:click={toggle}>logout</a>
    </Dropdown>
</DropdownShell>

<style lang="scss">
  @use 'theme' as vars;

  a {
    text-decoration: none;
    padding: 1em 0.5em;
    display: block;
  }

  [aria-current] {
    position: relative;
    display: inline-block;
  }

  [aria-current]::after {
    position: absolute;
    content: '';
    width: calc(100% - 1em);
    height: 2px;
    background-color: vars.$main;
    display: block;
    bottom: 5px;
  }
</style>
