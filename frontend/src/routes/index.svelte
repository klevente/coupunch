<script context="module">
	export async function preload(page, session) {
		if (session.user.authenticated) {
			this.redirect(302, 'home');
		}
	}
</script>

<script>


</script>

<style lang="scss">
	h1, figure, p {
		text-align: center;
		margin: 0 auto;
	}

	h1 {
		font-size: 2.8em;
		text-transform: uppercase;
		font-weight: 700;
		margin: 0 0 0.5em 0;
	}

	figure {
		margin: 0 0 1em 0;
	}

	img {
		width: 100%;
		max-width: 400px;
		margin: 0 0 1em 0;
	}

	p {
		margin: 1em auto;
	}

	@media (min-width: 480px) {
		h1 {
			font-size: 4em;
		}
	}
</style>

<svelte:head>
	<title>Home Without Login</title>
</svelte:head>

<h1>Coupunch landing page</h1>


<p><strong>Try editing this file (src/routes/index.svelte) to test live reloading.</strong></p>
