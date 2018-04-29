<template>
  <div class="main_page_container">
    <SearchBar></SearchBar>
    <ProductList id="product_list" :products="productList"></ProductList>
    <LogInForm></LogInForm>
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
        productList: []
      }
    },
    methods: {
      loadItemsList() {
        var t = this;
        axios.get('/api/product').then(function(response){
          t.productList = response.data;
          console.log(response.data)
        });
      }
    },
    mounted : function() {
      console.log('loadingProductList');
      this.loadItemsList();
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
