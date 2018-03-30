<template>
  <div class="items_list_container">
    <ul>
      <li v-for="item in items">
        {{ item.message }}
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'ItemsList',
    data () {
      return {
        items: [
          { message: 'Foo' },
          { message: 'Bar' }
        ]
      }
    }

  }
</script>

<style scoped>
  .items_list_container {
    border: black solid 3px;
  }
</style>
