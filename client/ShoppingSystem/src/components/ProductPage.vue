<template>
	<div v-if="!isLoading">
		<div class="product_page_container container">
			<div class="product_display_container">
				<div class="image_container">
					<img class="product_image" src="../assets/logo.png" v-if="!product.imageUrl">
					<img class="product_image" v-bind:src="product.imageUrl" v-if="product.imageUrl">
				</div>
        <h1>{{ product.name }}</h1>
				<div class="product_description_container round_border w-75 centered">
					{{ product.description }}
				</div>
        <div class="add_to_cart_container">
          <AddToCartButton :productId="productId" @add-to-cart="$emit('add-to-cart', $event)"></AddToCartButton>
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

}

.image_container {
  width: 40%;
  left: 50%;
  margin: auto;
}

.product_image {
	object-fit: contain;
	width: 100%;
	height: 100%;
}


.add_to_cart_container {
	width: 20%;
  margin: auto;
}

.product_description_container {
  margin: auto;
  margin-top: 10px;
  margin-bottom: 10px;

  padding-top: 5px;
  padding-bottom: 5px;

  background-color: rgba(0, 0, 0, 0.1);
}

</style>
