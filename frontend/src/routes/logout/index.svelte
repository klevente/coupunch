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
    import { logout } from '../../util/logout'

    logout('/');

</script>
