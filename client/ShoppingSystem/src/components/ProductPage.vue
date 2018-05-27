<template>
	<div v-if="!isLoading">
		<div class="product_page_container">
			<h1>{{ product.name }}</h1>
			<div class="product_display_container">
				<div class="image_container">
					<img class="product_image" src="../assets/logo.png" v-if="!product.imageUrl">
					<img class="product_image" v-bind:src="product.imageUrl" v-if="product.imageUrl">
				</div>
				<div class="product_description_container round_border">
					{{ product.description }}
					<div class="add_to_cart_container">
						<AddToCartButton :productId="productId" @add-to-cart="$emit('add-to-cart', $event)"></AddToCartButton>
					</div>
				</div>
			</div>
		</div>
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
			axios.get('/api/product/id/' + this.productId).then(function(response){
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
.product_page_container {

}

.product_display_container {
	position: absolute;
	top: 20%;
	left: 50%;
	transform: translate(-50%);

	width: 80vw;
	height: 40vh;
}

.image_container {
	float: left;
	display: inline-block;
	width: 40%;
	height: 100%;
}

.product_image {
	object-fit: contain;
	width: 100%;
	height: 100%;
}

.product_description_container {
	display: inline-block;
	width: 40%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.2);
}

.add_to_cart_container {
	width: 50%;
	transform: translate(50%);
}

</style>
