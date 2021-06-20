<script>
    import { createEventDispatcher } from 'svelte';
    import { Tab, Loading } from 'attractions';

    const dispatch = createEventDispatcher();

    export let productGroups;
    export let defaultGroup;
    export let selectedGroup;

</script>

<nav class="mobile padded">
    <Tab
            class={$selectedGroup.id === defaultGroup.id && 'selected'}
            value={defaultGroup}
            bind:group={$selectedGroup}
            name="nav-mobile"
    >
        {defaultGroup.name}
    </Tab>
    {#if $productGroups.isLoading()}
        <Loading />
    {/if}
    {#if $productGroups.hasData()}
        {#each $productGroups.data as productGroup}
            <Tab
                    class={$selectedGroup.id === productGroup.id && 'selected'}
                    value={productGroup}
                    bind:group={$selectedGroup}
                    name="nav-mobile"
            >
                {productGroup.name}
            </Tab>
        {/each}
    {/if}
</nav>

<style src="../../../../static/css/routes/products/_components/product-group-list-mobile.scss"></style>
