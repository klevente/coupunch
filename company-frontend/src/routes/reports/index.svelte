<script>
    import { onMount } from 'svelte';
    import { H1, Button } from 'attractions';
    import Dynamic from 'frontend-library/components/dynamic.svelte';
    import Viewmodel from './_viewmodel';

    const viewmodel = new Viewmodel();
    const { url, iframeUrl } = viewmodel;

    onMount(async () => {
        await viewmodel.getAll();
    });
</script>

<svelte:head>
    <title>Reports</title>
</svelte:head>

<header>
    <H1>Reports</H1>
    <Dynamic data={url}>
        <Button slot="data" let:data href={data} target="_blank" filled>Go to Metabase</Button>
    </Dynamic>
</header>
<section>
    <Dynamic data={iframeUrl}>
        <div slot="data" let:data>
            <iframe
                    src={data}
                    width="100%"
                    height="500px"
                    allowtransparency
            ></iframe>
        </div>
    </Dynamic>
</section>

<style lang="scss">
  header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  iframe {
    border: none;
  }

  iframe:focus {
    outline: none;
  }

  iframe[seamless] {
    display: block;
  }
</style>
