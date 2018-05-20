<template >
	<div>
		<div class="toolbar">
			<span>
			<button @click="$emit('login')" class="toolbar-item" v-if="!isLogedIn">Log in</button>
			<button v-bind:class="{activated: showCart}"
		   @click="toggleCartVisible"
		   class="cart-toggle toolbar-item">
				Cart({{ itemsInCart }})
			</button>
			<button @click="toggleUserMenuVisible" :class="{activated: showUserMenu}"class="toolbar-item" v-if="isLogedIn">{{ user.name }}</button>
			</span>
			<span>
				<button @click="openSearch" class="toolbar-item">Search</button>
				<button v-if="isAdmin" @click="openAdminPage" class="toolbar-item">Admin page</button>
			</span>
		</div>

		<!-- USER MENU -->
		<div class="user-menu-container" v-if="showUserMenu">
			<button class="user-menu">Orders</button>
			<button class="user-menu">Preferences</button>
			<button class="user-menu" v-on:click="$emit('logout')">Logout</button>
		</div>

		<!-- CART -->
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
					<button v-on:click="$emit('open-checkout')"
                  @click="toggleCartVisible">Checkout</button>
				</div>
			</div>
			<ul class="nav navbar-nav navbar-left">
				<li class="nav-item">
					<a v-if="isAdmin" @click="openAdminPage">Admin page</a>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right" >
				<li class="nav-item dropdown" :class="{active: showCart}">
					<a data-toggle="dropdown" class="dropdown-toggle" @click="toggleCartVisible">
						Cart({{ itemsInCart }})
					</a>
				<div class="dropdown-menu">
					<div v-if="cart.length > 0" class="container">
						<div class="row">
							<div class="col-xs-6">
								Products
							</div>
							<div class="col-xs-4">
								{{cartPrice}}
							</div>
							<div class="col-xs-2">
								<a @click="$emit('clear-cart')"><span class="glyphicon glyphicon-remove"></span></a>
							</div>
						</div>
					</div>
					<div v-else>Cart is empty</div>
				</div>
				</li>
				<li class="nav-item dropdown" :class="{active: showUserMenu}" >
					<a data-toggle="dropdown" class="dropdown-toggle" @click="toggleUserMenuVisible" v-if="isLogedIn">{{ user.name }}<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li>
							<a>Orders</a>
						</li>
						<li>
							<a>Preferences</a>
						</li>
						<li>
							<a @click="$emit('logout')">Logout</a>
						</li>
					</ul>
				</li>
				<li class="nav-item">
					<a @click="$emit('login')" v-if="!isLogedIn">Log in</a>
				</li>
			</ul>
		</nav>

		<!-- USER MENU 
	  <div class="user-menu-container" v-if="showUserMenu">
	  <button class="user-menu">Orders</button> 
	  <button class="user-menu">Preferences</button>
	  <button class="user-menu" v-on:click="$emit('logout')">Logout</button>
	  </div>
		-->

		<!-- CART 
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
		-->
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
			showCart: false,
			showUserMenu: false
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
			var count = 0;
			for (var i = 0; i < this.cart.length; i++) {
				count += this.cart[i][1];
			}
			return count;
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
		openSearch: function() {
			this.$router.push({name: 'Search'});
		},
		toggleCartVisible: function() {
			this.showUserMenu = false;
			this.showCart = !this.showCart;
		},
		toggleUserMenuVisible: function() {
			this.showCart = false;
			this.showUserMenu = !this.showUserMenu;
		}
	}
}
</script>

<style scoped>

.dropdown.active .dropdown-menu {
    display: block;
}

.cart.container {
	width: 600px;
	height: 400px;
}

/*
.toolbar-item {
	height: 100%;
	margin-left: 5px;
}

button.toolbar-item {
	background: #3498DB;
	color: #ffffff;
	border: solid 1px gray;
}

button.toolbar-item.activated,
button.user-menu {
	background: #50aaee;
	color: #ffffff;
	border: solid 1px gray;
}
*/
.toolbar {
	position: fixed;
	top: 0;
	left: 0;
	height: 40px;
	width: 100%;
	/*
	display: flex;
	justify-content: space-between;
	flex-direction: row-reverse;
	background-color: lightgray;
	*/
}


/*
div.cart-container {
}

div.cart-container, div.user-menu-container {
	background: #70ccff;
	position: fixed;
	top: 40px;
	right: 0;
}

div.user-menu-container {
	display: flex;
	flex-direction: column;
}

div.user-menu-container button {
	height: 40px;
}

div.cart {
	min-height: 200px;
	min-width: 200px;
	display: flex;
	max-height: 400px;
	flex-direction: column;
}

div.cart div.cart-list {
	overflow: auto;
	flex: 1;
}

div.cart div.checkout {
	flex: 0;
	height: 64px;
	margin: 10px;
}

div.cart-item {

}
*/
</style>

