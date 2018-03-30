<template>
  <div class="search_bar_container">
    <input class="search_bar" type="text" :placeholder="placeholder">

  </div>
</template>

<script>
  export default {
    name: 'SearchBar',
    data () {
      return {
        placeholder: 'Search'
      }
    }

  }
</script>

<style scoped>
  .search_bar {
    width: 20vw;
    height: 2vw;
    font-size: 1.5vw;
  }

</style>
