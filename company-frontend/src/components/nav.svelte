<script>
    import { stores } from '@sapper/app';
    import { H1 } from 'attractions';

    export let segment;

    const { session } = stores();
</script>

<nav>
    {#if $session.user.authenticated}
        <H1>{$session.user.companyName}</H1>
        <ul>
            <li><a aria-current="{segment === 'home' ? 'page' : undefined}" href="home">home</a></li>
            <li><a aria-current="{segment === 'logout' ? 'page' : undefined}" href="logout">logout</a></li>
        </ul>
    {:else}
        <ul>
            <li><a aria-current="{segment === undefined ? 'page' : undefined}" href=".">home</a></li>
            <li><a aria-current="{segment === 'about' ? 'page' : undefined}" href="about">about</a></li>
            <li><a aria-current="{segment === 'login' ? 'page' : undefined}" href="login">login</a></li>
        </ul>
    {/if}
</nav>

<style lang="scss">
  @use 'theme' as vars;

  nav {
    display: flex;
    align-items: center;
    border-bottom: 1px solid rgba(255,62,0,0.1);
    font-weight: 300;
    padding: 0 1em;
  }

  :global(h1) {
    margin: 0 !important;
  }

  ul {
    margin: 0;
    padding: 0;
  }

  /* clearfix */
  ul::after {
    content: '';
    display: block;
    clear: both;
  }

  li {
    display: block;
    float: left;
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
    bottom: -1px;
  }

  a {
    text-decoration: none;
    padding: 1em 0.5em;
    display: block;
  }
</style>
