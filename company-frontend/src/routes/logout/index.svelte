<script context="module">
    import { create } from '@beyonk/sapper-httpclient';
    export async function preload(page, session) {
        const api = create();
        await api
            .transport(this.fetch)
            .endpoint('users/logout')
            .post();
    }
</script>

<script>
    import { goto, stores } from '@sapper/app';

    const { session } = stores();
    session.set({
        user: {
            authenticated: false,
            scope: [],
        }
    });
    goto('/');

</script>
