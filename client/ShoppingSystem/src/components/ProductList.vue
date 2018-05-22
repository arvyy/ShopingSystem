<template>
	<div>
		<div class="row justify-content-center">
			<div class="product_container" v-for="product in products.content">
				<Product :name=product.name :price=product.price :productId=product.productId v-on:add-to-cart="$emit('add-to-cart', $event)" v-on:product-click="openProductPage(product.productId)"></Product>
			</div>
		</div>
		<b-pagination align="center" @input="$emit('set-page', $event - 1)" size="md" :total-rows="products.totalElements" :value="products.number + 1" :per-page="products.size"/>
		<!--div class="row justify-content-center" v-if="products.totalPages > 1">
			<button v-if="!products.first" v-on:click="$emit('set-page', 0)">1</button>
			<span v-if="products.number > 2">...</span>
			<button v-if="products.number > 1" v-on:click="$emit('set-page', products.number - 1)">{{ products.number }}</button>
			<span>{{ products.number + 1 }}</span>
			<button v-if="products.number < products.totalPages - 2" v-on:click="$emit('set-page', products.number + 1)">{{ products.number + 2 }}</button>
			<span v-if="products.number < products.totalPages - 3">...</span>
			<button v-if="!products.last" v-on:click="$emit('set-page', products.totalPages - 1)">{{ products.totalPages }}</button>
		</div -->
	</div>
</template>


<script>
import Product from "./Product";

export default {
	components: {Product},
	name: 'ProductList',
	props : ['products'],
	data () {
		return {

		}
	},
	methods: {
		openProductPage: function(productId) {
			this.$router.push({name: 'ProductPage', params: { productId: productId }});
		}
	}

}
</script>

<style scoped>
.product_list_container {
	border: black solid 0px;
}

.product_container {
	margin: 10px;
	width: auto;
	display: inline-block;


}
</style>
