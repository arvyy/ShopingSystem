<template>
	<div class="product_container round_border">
		<div class="image_container">
			<a v-on:click="$emit('product-click')"><img class="image" src="./../assets/logo.png"></a>
		</div>
		<div class="product_info_container">
			<div class="name"><a v-on:click="$emit('product-click')">{{ name }}</a></div>
			<div class="price">Price: {{ price }} $</div>
			<AddToCartButton class="add_to_cart_button" :productId="productId" @add-to-cart="$emit('add-to-cart', $event)" ></AddToCartButton>
		</div>
	</div>
</template>

<script>
import AddToCartButton from "./AddToCartButton";

export default {
	components: {AddToCartButton},
	name: 'Product',
	props: ['name', 'price', 'image', 'productId'],
	data () {
		return {
		}
	}

}
</script>

<style scoped>
.product_container {
	border: rgb(208, 208, 208) solid 2px;
	width: 250px;
	height: 300px;
	overflow: hidden;
	margin: 0;
}

.product_info_container {
	background-color: rgba(0, 0, 0, 0.1);
	width: 100%;
	height: 100%;
	padding-top: 2%;
	padding-bottom: 2%;
}

.name {
	font-weight: bold;
	font-size: 16px;
	margin-bottom: 10px;
}

.price {
  font-size: 14px;
	margin-bottom: 3%;
}

.add_to_cart_button {
	width: 50%;
	transform: translateX(50%);
}

.image_container {
	width: 100%;
	height: 60%;
}

img {
	object-fit: contain;
	width: 100%;
	height: 100%;
}
</style>

