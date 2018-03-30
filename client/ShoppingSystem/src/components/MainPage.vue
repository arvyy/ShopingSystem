<template>
  <div class="main_page_container">
    <SearchBar></SearchBar>
    <ItemsList id="items_list"></ItemsList>

  </div>
</template>

<script>
  import SearchBar from './SearchBar.vue'
  import ItemsList from './ItemsList.vue'

  export default {
    name: 'MainPage',
    data () {
      return {

      }
    },
    components: {
      SearchBar,
      ItemsList
    }
  }


</script>

<style scoped>
  #items_list {
    text-align: left;

  }

</style>
