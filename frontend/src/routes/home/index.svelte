<script>
    import { stores } from '@sapper/app';
    import { Headline, Subhead } from 'attractions';

    const { session } = stores();

</script>

<svelte:head>
    <title>Home</title>
</svelte:head>

<header>
    <Headline>Hello {$session.user.username}!</Headline>
    <Subhead>Show this QR-code to the cashier at supported businesses.</Subhead>
</header>
<section>
    <img src="api/users/current/qr" alt="current user qr code">
</section>

<style lang="scss">
  @use 'theme' as vars;

  img {
    margin: 10px auto 20px;
    display: block;
    padding: 5px;
    border: vars.$main solid 3px;
    border-radius: vars.$snackbar-radius;
  }
</style>
