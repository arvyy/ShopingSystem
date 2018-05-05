<template>
	<div>
		<router-view @add-to-cart="addToCart" />
		<TopBar />
	</div>
</template>

<script>
	import TopBar from "./TopBar"
	export default {
		name: 'MainPage',
		components: {
			TopBar
		},
		methods: {
			addToCart : function(productId) {
				console.log('add to cart');
			}
		}
	}
</script>

<style>
</style>
