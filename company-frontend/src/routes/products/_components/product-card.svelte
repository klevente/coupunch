<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button } from 'attractions';
    import { BoxIcon, XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let product;

    function onEditClick() {
        dispatch('edit', product);
    }

    function onDeleteClick() {
        dispatch('delete', product);
    }

</script>

<div class="product-card">
    <Card>
        <div class="icon flex-mr">
            <BoxIcon size="24"/>
        </div>
        <div class="name flex-mr">{product.name}</div>
        <div class="subtitle">${product.price}</div>
        <div class="flex-spacer"></div>
        <Button round neutral on:click={onEditClick}>
            <EditIcon size="20"/>
        </Button>
        <Button round neutral on:click={onDeleteClick}>
            <XIcon size="20"/>
        </Button>
    </Card>
</div>

<style lang="scss">
  @use 'theme' as vars;

  .product-card {

    width: 100%;

    margin-bottom: 1em;
    /*cursor: pointer;*/

    :global .card {
      transition: 150ms box-shadow;
      &:hover {
        box-shadow: vars.$shadow-raised;
      }

      width: 100% !important;
      padding: 0.5em;
      display: flex;
      align-items: center;
    }

    .icon {
      padding: 0.8em;
      border-radius: 50%;
      background: vars.$main;
      color: white;
      align-self: center;

      :global svg {
        display: block;
      }
    }

    .coupons {

    }
  }
</style>
