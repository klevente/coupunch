<script context="module">
    import { create } from '@beyonk/sapper-httpclient';
    export async function preload(page, session) {
        const api = create();
        await api
            .context(this)
            .endpoint('users/logout')
            .post();
    }
</script>

<script>
    import { onMount } from 'svelte';
    import { goto, stores } from '@sapper/app';

    const { session } = stores();
    session.set({
        user: {
            authenticated: false,
            scope: [],
        }
    });
    // goto('/');
    // window.location.href = '/';
    onMount(() => {
        window.location.href = '/';
    })
</script>
