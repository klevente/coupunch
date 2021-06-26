<script context="module">
	import { create } from '@beyonk/sapper-httpclient';

	export async function preload(page, session) {
		if (page.path === '/logout') {
			return;
		}
		if (session.user.authenticated && !session.user.id) {
			try {
				const api = create();
				const currentUser = await api
						.context(this)
						.endpoint('couponmanager/users/current')
						.accessDenied(e => {
							this.redirect(302, '/logout');
						})
						.get();

				return { currentUser };
			} catch (e) {
				console.error(e);
				this.redirect(302, '/logout');
			}
		}

		return {
			currentUser: {}
		};
	}
</script>

<script>
	import Nav from '../components/nav.svelte';
	import { stores, goto } from '@sapper/app';
	import { tick } from 'svelte';
	import routes from '../routes';
	import { guard } from "@beyonk/sapper-rbac";

	export let segment;
	export let currentUser;

	const { page, session } = stores();

	if (currentUser) {
		session.update(v => {
			v.user = { ...v.user, ...currentUser };
			return v;
		});
	}

	const options = {
		routes,
		deny: () => goto('/login'),
	}

	page.subscribe(async v => {
		await tick();
		guard(v.path, $session.user, options);
	});

</script>

<Nav {segment}/>

<main>
	<slot></slot>
</main>

<style src="../..//static/css/routes/_layout.scss"></style>
