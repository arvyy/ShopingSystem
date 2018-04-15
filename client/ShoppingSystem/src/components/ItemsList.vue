<template>
  <div class="items_list_container">
    <ul>
      <li v-for="item in items">
        {{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
/*
	Gal vertetu pervadint i productList?
*/
    name: 'ItemsList',
	props : ['items'],
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
  .items_list_container {
    border: black solid 3px;
  }
</style>
