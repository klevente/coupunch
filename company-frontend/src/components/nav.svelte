<script>
	import { stores } from '@sapper/app';

	export let segment;

	const { session } = stores();
</script>

<nav>
	<ul>
		{#if $session.user.authenticated}
			<li><a aria-current="{segment === 'home' ? 'page' : undefined}" href="home">home</a></li>
			<li><a aria-current="{segment === 'logout' ? 'page' : undefined}" href="logout">logout</a></li>
		{:else}
			<li><a aria-current="{segment === undefined ? 'page' : undefined}" href=".">home</a></li>
			<li><a aria-current="{segment === 'about' ? 'page' : undefined}" href="about">about</a></li>
			<li><a aria-current="{segment === 'login' ? 'page' : undefined}" href="login">login</a></li>
		{/if}
	</ul>
</nav>

<style src="../../static/css/components/nav.scss"></style>
