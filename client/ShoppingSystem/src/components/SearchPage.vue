<template>
  <div class="main_page_container">
    <SearchBar v-on:do-search="onSearch"></SearchBar>
    <ProductList id="product_list" 
		:products="productPages" 
		@add-to-cart="$emit('add-to-cart', $event)"
		@set-page="loadItemsList" />
    <!--LogInForm></LogInForm -->
  </div>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios'
  import SearchBar from './SearchBar.vue'
  import ProductList from './ProductList.vue'
  import LogInForm from './LogInForm.vue'

  export default {
    name: 'MainPage',
    data () {
      return {
		searchText : '',
		productsPerPage: 20,
        productPages: {}
      }
    },
    methods: {
		onSearch : function(searchText) {
			this.searchText = searchText;	
			this.loadItemsList(0);
		},
      loadItemsList(page) {
        var t = this;
        axios.get('/api/product', {
			params: {
				text: t.searchText,
				size: t.productsPerPage,
				page: page
			}
		}).then(function(response){
          t.productPages = response.data;
          console.log(response.data)
        });
      }
    },
    mounted : function() {
      console.log('loadingProductList');
      this.loadItemsList(0);
    },

    components: {
      SearchBar,
      ProductList,
      LogInForm
    }
  }


</script>

<style scoped>
  #items_list {
    text-align: left;

  }

</style>
