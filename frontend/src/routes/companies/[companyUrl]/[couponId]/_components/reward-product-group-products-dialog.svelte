<script>
    import { Dialog, Modal, Button } from 'attractions';
    import { EasyTable, Row, Column } from 'frontend-library/components/table';

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
</script>

<Modal bind:open={modalOpen}>
    <Dialog {title} closeCallback={close}>
        <EasyTable
                columns={[
                    { name: 'Name' },
                    { name: 'Price' },
                    { name: 'Discounted Price' }
                ]}
                items={products}
        >
            <Row slot="row" let:row>
                <Column>{row.name}</Column>
                <Column><s>${row.originalPrice}</s></Column>
                <Column>${row.discountedPrice}</Column>
            </Row>
        </EasyTable>
    </Dialog>
</Modal>

<style lang="scss">

</style>
