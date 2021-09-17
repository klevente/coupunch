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

<H1>Reports</H1>
<section>
    <Dynamic data={url}>
        <div class="metabase-header" slot="data" let:data>
            <Button href={data} target="_blank" filled>Go to Metabase</Button>
        </div>
    </Dynamic>
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
  .metabase-header {
    display: flex;
    justify-content: space-between;
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
