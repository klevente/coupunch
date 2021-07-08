<script>
    import { createEventDispatcher } from 'svelte';
    import { Tab, Loading } from 'attractions';
    import Dynamic from '../../../components/dynamic.svelte';

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
    <Dynamic data={productGroups}>
        <svelte:fragment slot="data" let:item>
            <Tab
                    class={$selectedGroup.id === item.id && 'selected'}
                    value={item}
                    bind:group={$selectedGroup}
                    name="nav-mobile"
            >
                {item.name}
            </Tab>
        </svelte:fragment>
    </Dynamic>
</nav>

<style src="../../../../static/css/routes/products/_components/product-group-list-mobile.scss"></style>
