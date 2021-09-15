<script>
    import { H1 } from 'attractions';
</script>

<svelte:head>
    <title>Reports</title>
</svelte:head>

<H1>Reports</H1>
<section>
    <iframe
            src="http://localhost:8086/orgs/4aa4774be8571abf/dashboards/0826b41b3f271000?present=true"
            width="100%"
            height="500px"
    ></iframe>
</section>

<style lang="scss">
  iframe:focus {
    outline: none;
  }

  iframe[seamless] {
    display: block;
  }
</style>
