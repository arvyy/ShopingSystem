<template >
	<div>
		<b-modal id="cart" title="Cart" hide-footer ref="cartModal">
			<p v-if="cart.length == 0" class="my-4">Cart is empty</p>
			<b-container v-if="cart.length > 0">
				<b-table striped hover outlined :items="cartTableItems">
					<template slot="removeCartItemButton" slot-scope="cell">
						<b-btn size="sm" @click.stop="removeCartItem(cell.index)">Remove</b-btn>
					</template>
				</b-table>
	<b-button @click="openCheckout">Checkout {{cartPrice}}$</b-button>
			</b-container>
		</b-modal>
		<b-navbar toggleable="md" variant="info">
			<b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
			<b-navbar-brand @click="openSearch">Ill Shop</b-navbar-brand>
			<b-collapse is-nav id="nav_collapse">
			<b-navbar-nav>
				<b-nav-item  @click="openAdminPage" v-if="isAdmin">Admin page</b-nav-item>
			</b-navbar-nav>
			<b-navbar-nav class="ml-auto">
				<b-nav-item v-b-modal.cart>Cart({{ itemsInCart }})</b-nav-item>
				<b-nav-item v-if="!isLoggedIn" @click="$emit('login')" right>Login</b-nav-item>

				<b-nav-item-dropdown v-if="isLoggedIn" :text="user.name" right>
					<b-dropdown-item>Orders</b-dropdown-item>
					<b-dropdown-item @click="openPreferencesPage">Preferences</b-dropdown-item>
					<b-dropdown-item @click="$emit('logout')">Logout</b-dropdown-item>
				</b-nav-item-dropdown>
			</b-navbar-nav >
			</b-collapse>
		</b-navbar>
		<!--div class="toolbar">
			<span>
			<button @click="$emit('login')" class="toolbar-item" v-if="!isLoggedIn">Log in</button>
			<button v-bind:class="{activated: showCart}"
		   @click="toggleCartVisible"
		   class="cart-toggle toolbar-item">
				Cart({{ itemsInCart }})
			</button>
			<button @click="toggleUserMenuVisible" :class="{activated: showUserMenu}"class="toolbar-item" v-if="isLoggedIn">{{ user.name }}</button>
			</span>
			<span>
				<button @click="openSearch" class="toolbar-item">Search</button>
				<button v-if="isAdmin" @click="openAdminPage" class="toolbar-item">Admin page</button>
			</span>
		</div>

		<!-- USER MENU -->
		<div class="user-menu-container" v-if="showUserMenu">
			<button class="user-menu">Orders</button>
			<button class="user-menu" @click="openPreferencesPage">Preferences</button>
			<button class="user-menu" v-on:click="$emit('logout')">Logout</button>
		</div>


		<!-- CART -->
		<!--div class="cart-container" v-if="showCart">
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
					<button @click="openCheckoutPage">Checkout</button>
				</div>
			</div>
		</div -->
	</div>
</template>

<script>
export default {
	name: 'TopBar',
	props: [
		'user',
		'cart'
	],
	data : function() {
		return {
			showCart: false,
			showUserMenu: false,
			fields: [{
				key: "product",
				label: 'Product'
			}, {
				key: 'price',
				label: 'Price'
			}, {
				key: 'removeCartItemButton',
				label: ""
			}]
		};
	},
	computed: {
		cartTableItems: function() {
			var t= this;
			return this.cart.map(function(entry){
				return {
					"product": entry[0].name + " x" + entry[1],
					"price": t.priceFormat(entry[0].price * entry[1]),
					"removeCartItemButton": ''
				}
			});
		},
		cartPrice: function() {
			var sum = 0;
			for (var i = 0; i < this.cart.length; i++) {
				sum += this.cart[i][0].price * this.cart[i][1];
			}
			return this.priceFormat(sum);
		},
		isAdmin: function() {
			return this.isLoggedIn && this.user.admin;
		},
		isLoggedIn: function() {
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
		removeCartItem: function(index) {
			this.$emit('remove-cart-item', this.cart[index][0].productId);
		},
		priceFormat: function(p) {
			return parseFloat(p * 100 / 100).toFixed(2);
		},
		openCartItemPage: function(productId) {
			console.log(productId);
			this.$router.push({name: 'ProductPage', params: { productId: productId }});
		},
    openPreferencesPage: function() {
      if (this.isLoggedIn) {
        this.$router.push({name: 'Preferences'});
      }
      else {
        this.$router.push({name: 'Login'});
      }
    },
		openAdminPage: function() {
			this.$router.push({name: 'UsersForm'});
		},
		openSearch: function() {
			this.$router.push({name: 'Search'});
		},
		openCheckout: function() {
			this.$refs.cartModal.hide();
			if (this.isLoggedIn) {
        this.$router.push({name: 'Checkout'});
      }
      else {
        this.$router.push({name: 'Login'});
      }
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

div.toolbar {
	display: flex;
	position: fixed;
	justify-content: space-between;
	flex-direction: row-reverse;
	top: 0;
	left: 0;
	height: 40px;
	width: 100%;
	background-color: lightgray;
}

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
</style>

