<script>
    import { Dialog, Modal, Button } from 'attractions';
    import { EasyTable, Row, Column } from 'frontend-library/components/table';
    import { sortedReactive } from 'frontend-library/viewmodel/transformations';
    import { sortByStore } from 'frontend-library/viewmodel/transformations/stores';


    export function open(productGroup) {
        products = productGroup.products;
        title = productGroup.name;
        modalOpen = true;
    }

    export function close() {
        modalOpen = false;
        products = [];
        title = '';
    }

    let modalOpen = false;
    let products = [];
    let title = '';

    const sortBy = sortByStore('name');
    $: sortedProducts = sortedReactive({
        array: products,
        sortBy: $sortBy
    });
</script>

<Modal bind:open={modalOpen}>
    <Dialog {title} closeCallback={close}>
        <EasyTable
                {sortBy}
                columns={[
                    { name: 'Name', property: 'name' },
                    { name: 'Price', property: 'price' }
                ]}
                items={sortedProducts}
        >
            <Row slot="row" let:row>
                <Column>{row.name}</Column>
                <Column>${row.price}</Column>
            </Row>
        </EasyTable>
    </Dialog>
</Modal>

<style lang="scss">

</style>
