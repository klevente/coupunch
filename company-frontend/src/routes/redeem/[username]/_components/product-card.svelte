<script>
    import { createEventDispatcher } from 'svelte';
    import { Card, Button } from 'attractions';
    import { BoxIcon, XIcon, EditIcon } from 'svelte-feather-icons';

    const dispatch = createEventDispatcher();

    export let product;

    function onClick() {
        dispatch('click', {
            product
        });
    }

</script>

<div class="product-card flex-mr" on:click={onClick}>
    <Card>
        <div class="icon flex-mr">
            <BoxIcon size="24"/>
        </div>
        <div>
            <div class="name flex-mr">{product.name}</div>
            <div class="subtitle">${product.price}</div>
        </div>
    </Card>
</div>

<style lang="scss">
  @use 'theme' as vars;

  .product-card {

    cursor: pointer;

    :global .card {
      transition: 150ms box-shadow;
      &:hover {
        box-shadow: vars.$shadow-raised;
      }

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
  }
</style>
