<template>
	<div class="main_page_container">
			<topBar ref="topBar"
      :user="currentUser"
			:cart="cart"
			@login="onLogin"
			@logout="onLogout"
			@clear-cart="onClearCart"
	  @remove-cart-item="onRemoveCartItem" />

		<router-view :user="currentUser" 
		   class="container"
			@email-update="loadCurrentUser"
			@add-to-cart="addToCart" 
			@order-complete="loadCart" />
	</div>
</template>

<script>
import TopBar from "./TopBar"
import axios from 'axios'
export default {
	name: 'MainPage',
	components: {
		'topBar': TopBar
	},
	data : function() {
		return {
			currentUser: null,
			cart: []
		};
	},
	methods: {
		loadCart: function() {
			var t = this;
			axios.get('/api/shoppingcart').then(function(resp){
				var cart = resp.data;
				t.cart = cart;
			});
		},
		addToCart : function(productId) {
			var t = this;
			var data = [{
				Id: productId,
				Amount: 1
			}];
			axios.post('/api/shoppingcart', data).then(function(resp){
				var cart = resp.data;
				t.cart = cart;
			});
		},
		onLogin: function() {
			this.$router.push({name: 'Login'});
		},
		onLogout: function() {
			axios.post('/logout');
			this.currentUser = null;
      this.$router.push({name: 'Search'});
		},
		onClearCart: function() {
			axios.delete('/api/shoppingcart');
			this.cart = [];
		},
		onRemoveCartItem: function(productId) {
			var t = this;
			axios.delete('/api/shoppingcart/' + productId).then(function(resp){
				t.loadCart();
			});
		},
		loadCurrentUser: function() {
			var t = this;
			axios.get('/api/user/me').then(function(resp){
				t.currentUser = resp.data;
			});
		}
	},
	mounted: function() {
		var t = this;
		this.loadCurrentUser();
		this.loadCart();
	}
}
</script>

<style scoped>
</style>

<!--Global style-->
<style>
  .round_border {
    border-radius: 4px;
  }
</style>
