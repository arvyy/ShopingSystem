<template>
  <div class="product_list_container">
    <ul>
      <li v-for="product in products">
        {{ product.name }}
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
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
    border: black solid 3px;
  }
</style>
