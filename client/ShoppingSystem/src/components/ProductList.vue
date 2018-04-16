<template>
  <div class="product_list_container">
    <h1>Products</h1>
    <div class="product_container" v-for="product in products">
        <Product :name=product.name :price=product.price></Product>
    </div>
  </div>
</template>


<script>
  import Product from "./Product";

  export default {
    components: {Product},
    name: 'ProductList',
	  props : ['products'],
    data () {
      return {
/*
        items: [
          { message: 'Foo' },
          { message: 'Bar' }
        ]
*/
      }
    }

  }
</script>

<style scoped>
  .product_list_container {
    border: black solid 0px;
  }

  .product_container {
    margin: 10px;
    width: auto;
    display: inline-block;


  }
</style>
