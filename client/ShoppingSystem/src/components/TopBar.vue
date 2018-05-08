<template >
	<div>
		<div class="toolbar">
			<button v-on:click="$emit('login')" class="toolbar-item" v-if="!isLogedIn">Log in</button>
			<button v-on:click="$emit('logout')" class="toolbar-item" v-if="isLogedIn">Log out</button>
			<span class="toolbar-item" v-if="isLogedIn">{{ user.name }}</span>
			<button v-if="isAdmin" @click="openAdminPage">Admin page</button>
			<button v-bind:class="{pressed: showCart}" 
		   @click="toggleCartVisible" 
		   class="cart-toggle toolbar-item">
				Cart({{ itemsInCart }})
			</button>
		</div>
		<div class="cart-container" v-if="showCart">
			<div v-if="cart.length == 0">Cart is empty</div>
			<div v-if="cart.length > 0" class="cart">
				<div class="cart-list">
					<table>
						<tr>
							<th colspan="2">Products</th>
							<th>{{ cartPrice }}</th>
							<th><button @click="$emit('clear-cart')">X</button></th>
						</tr>
						<tr v-for="cartitem in cart" class="cart-item">
							<td><a @click="openCartItemPage(cartitem[0].productId)">{{cartitem[0].name}}</a></td> 
							<td><span class="cart-count">x{{cartitem[1]}}</span></td>
							<td><span>{{cartitem[0].price}}</span></td>
							<td><button>X</button></td>
						</tr>	
					</table>
				</div>
				<div class="checkout">
					<button>Checkout</button>
				</div>
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
		cartPrice: function() {
			var sum = 0;
			for (var i = 0; i < this.cart.length; i++) {
				sum += this.cart[i][0].price * this.cart[i][1];
			}
			return parseFloat(sum * 100 / 100).toFixed(2);
		},
		isAdmin: function() {
			return this.isLogedIn && this.user.admin;
		},
		isLogedIn: function() {
			return (this.user && this.user.name)? true : false;
		},
		itemsInCart: function() {
			return this.cart.length;
		}
	},
	methods: {
		openCartItemPage: function(productId) {
			console.log(productId);
			this.$router.push({name: 'ProductPage', params: { productId: productId }});
		},
		openAdminPage: function() {
			this.$router.push({name: 'UsersForm'});
		},
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
	background-color: yellow;
}

div.cart {
	display: flex;
	max-height: 400px;
	flex-direction: column;
}

div.cart div.cart-list {
	overflow: auto;
	flex: 1;
}

div.cart div.ckeckout {
	flex: 0;
	height: 64px;
}

div.cart-item {

}

button.cart-toggle.pressed {
	background-color: yellow;
}
</style>
