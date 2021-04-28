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

<style src="../../static/css/components/nav.scss"></style>
