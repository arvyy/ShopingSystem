<template>
  <div class="main_page_container">
    <SearchBar></SearchBar>
    <ItemsList id="items_list" :items="itemsList"></ItemsList>
  </div>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios'
  import SearchBar from './SearchBar.vue'
  import ItemsList from './ItemsList.vue'

  export default {
    name: 'MainPage',
    data () {
      return {
		itemsList: []
      }
    },
	methods: {
		loadItemsList() {
			var t = this;
			axios.get('/api/product').then(function(response){
				t.itemsList = response.data;
			});
		}
	},
	mounted : function() {
		console.log('loadingItemsList');
		this.loadItemsList();
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
