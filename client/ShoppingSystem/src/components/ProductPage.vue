<template>
	<div v-if="!isLoading">
		<h1>{{ product.name }}</h1>
		<p>
			<img class="productImage" src="../assets/logo.png" v-if="!product.imageUrl">
			<img class="productImage" v-bind:src="product.imageUrl" v-if="product.imageUrl">
			{{ product.description }}
		</p>
	</div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'ProductPage',
    props: ['productId'],
    data () {
      return {
	  	isLoading: true,
		product: {}
      }
    },
	watch: {
		'$route': function(to, from) {
			this.loadProductInfo();
		}
	},
	methods: {
		loadProductInfo : function() {
			this.isLoading = true;
			var t = this;
			axios.get('/api/product/' + this.productId).then(function(response){
				t.product = response.data;
				t.isLoading = false;	
			});
		}
	},
	mounted : function() {
		this.loadProductInfo();
	}
  }
</script>

<style scoped>
	img.productImage {
		float: left;
	}
</style>
