<template >
	<div>
	<div class="toolbar">
		<span class="toolbar-item" v-if="isLogedIn">{{ user }}</span>
		<button v-on:click="$emit('login')" class="toolbar-item" v-if="!isLogedIn">Log in</button>
		<button v-on:click="$emit('logout')" class="toolbar-item" v-if="isLogedIn">Log out</button>
		<button v-bind:class="{pressed: showCart}" @click="toggleCartVisible" class="cart-toggle toolbar-item">Cart({{ itemsInCart }})</button>
	</div>
	<div class="cart-container" v-if="showCart">
		<div>
			<div v-for="cartitem in cart">{{cartitem[0].name}}: {{cartitem[1]}}</div>	
		</div>
	</div>
	</div>
</template>

<script>
  import axios from 'axios'
	export default {
		name: 'TopBar',
		props: [
			'user',
			'cart'
		],
		data : function() {
			return {
				showCart: false
			};
		},
		computed: {
			isLogedIn: function() {
				return this.user? true : false;
			},
			itemsInCart: function() {
				return this.cart.length;
			}
		},
		methods: {
			toggleCartVisible: function() {
				this.showCart = !this.showCart;
			}
		}
	}
</script>

<style scoped>
	.toolbar-item {
		height: 100%;
	}

	div.toolbar {
		display: flex;
		position: fixed;
		flex-direction: row-reverse;
		top: 0;
		left: 0;
		height: 40px;
		width: 100%;
		background-color: magenta;
	}

	div.cart-container {
		position: fixed;
		top: 40px;
		right: 0;
		height: 60px;
		width: 240px;
		background-color: yellow;
		overflow: auto;
	}

	button.cart-toggle.pressed {
		background-color: yellow;
	}
</style>
