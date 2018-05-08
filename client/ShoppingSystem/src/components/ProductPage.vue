<template>
	<div v-if="!isLoading">
		<h1>{{ product.name }}</h1>
		<p>
		<div class="left-container">
			<img class="productImage" src="../assets/logo.png" v-if="!product.imageUrl">
			<img class="productImage" v-bind:src="product.imageUrl" v-if="product.imageUrl">
			<AddToCartButton :productId="productId" @add-to-cart="$emit('add-to-cart', $event)"></AddToCartButton>
		</div>
		{{ product.description }}
		</p>
	</div>
</template>

<script>
import axios from 'axios'
import AddToCartButton from './AddToCartButton'
export default {
	name: 'ProductPage',
	components: {AddToCartButton},
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

.left-container {
	float: left;
}
</style>
