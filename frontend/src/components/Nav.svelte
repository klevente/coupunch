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
			<li><a aria-current="{segment === 'register' ? 'page' : undefined}" href="register">register</a></li>
		{/if}
	</ul>
</nav>

<style>
	nav {
		border-bottom: 1px solid rgba(255,62,0,0.1);
		font-weight: 300;
		padding: 0 1em;
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
		background-color: rgb(255,62,0);
		display: block;
		bottom: -1px;
	}

	a {
		text-decoration: none;
		padding: 1em 0.5em;
		display: block;
	}
</style>
