<script>
    import { onMount } from 'svelte';

    export let options;

    let ApexCharts;
    let loaded = false;

    onMount(async () => {
        ApexCharts = (await import('apexcharts')).default;
        window.ApexCharts = ApexCharts;
        loaded = true;
    });

    const chart = (node, options) => {
        if (!loaded) {
            return;
        }
        let newChart = new ApexCharts(node, options);
        newChart.render();

        return {
            update: (options) => newChart.updateOptions(options),
            destroy: () => newChart.destroy()
        };
    }
</script>

{#if loaded}
    <div use:chart={options}></div>
{/if}

<style lang="scss">

</style>
