<script context="module">
    import UserService from '../services/user-service';

    export async function preload(page, session) {
        if (page.path === '/logout') {
            return;
        }
        if (session.user.authenticated && !session.user.id) {
            try {
                const currentUser = await UserService.getCurrent(this);
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
        session.update(session => {
            session.user = { ...session.user, ...currentUser };
            return session;
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

<style lang="scss">
  @use 'global';
  @use 'theme' as vars;

  main {
    position: relative;
    max-width: 60em;
    background-color: vars.$background;
    padding: 2em;
    margin: 0 auto;
    box-sizing: border-box;
    max-height: 100%;
    min-height: calc(100vh - 50px - 1px);
  }
</style>
