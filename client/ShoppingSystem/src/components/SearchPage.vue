<template>
	<div class="main_page_container">
		<SearchBar v-on:do-search="onSearch" :categories="categories"></SearchBar>
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
	props: ['searchText', 'page', 'category'],
	data () {
		return {
			productsPerPage: 20,
			productPages: {},
			categories: []
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
				page: this.page,
				category: this.category
			};
		},
		onSearch : function(params) {
			var q = {
				text: params.searchText,
				category: params.category,
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
			axios.get('/api/product/page', {
				params: {
					text: t.searchText,
					category: t.category,
					size: t.productsPerPage,
					page: page
				}
			}).then(function(response){
				t.productPages = response.data;
				console.log(response.data)
			});
		},
		loadCategories: function() {
			var t = this;
			axios.get('/api/category').then(function(resp) {
				t.categories = resp.data;
			});
		}
	},
	mounted : function() {
		this.loadItemsList(this.page);
		this.loadCategories();
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
