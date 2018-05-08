<template>
	<div class="main_page_container">
		<SearchBar v-on:do-search="onSearch"></SearchBar>
		<ProductList id="product_list" 
			   :products="productPages" 
			   @add-to-cart="$emit('add-to-cart', $event)"
			   @set-page="onChangePage" />
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
	props: ['searchText', 'page'],
	data () {
		return {
			productsPerPage: 20,
			productPages: {}
		}
	},
	watch: {
		'$route': function(to, from) {
			this.loadItemsList(this.page);
		}
	},
	methods: {
		getQuery: function() {
			return {
				text: this.searchText,
				page: this.page
			};
		},
		onSearch : function(searchText) {
			var q = {
				text: searchText,
				page: 0
			};
			this.$router.push({name: 'Search', query: q});
		},
		onChangePage: function(page) {
			var q = this.getQuery();
			q.page = page;
			this.$router.push({name: 'Search', query: q});
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
		this.loadItemsList(this.page);
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

div.main_page_container {
	margin-top: 60px;
}

</style>
