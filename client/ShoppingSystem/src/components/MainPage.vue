<template>
	<div class="main_page_container">
		<router-view @add-to-cart="addToCart" />
			<TopBar :user="currentUser"
			:cart="cart"
			@login="onLogin"
			@logout="onLogout"
			@clear-cart="onClearCart"
      @open-checkout="onOpenCheckout"/>
	</div>
</template>

<script>
import TopBar from "./TopBar"
import axios from 'axios'
export default {
	name: 'MainPage',
	components: {
		TopBar
	},
	data : function() {
		return {
			currentUser: null,
			cart: []
		};
	},
	methods: {
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
		},
		onClearCart: function() {
			axios.delete('/api/shoppingcart');
			this.cart = [];
		},
    onOpenCheckout: function() {
      this.$router.push({name: 'Checkout'});
    }
	},
	mounted: function() {
		var t = this;
		axios.get('/api/user/me').then(function(resp){
			t.currentUser = resp.data;
		});
	}
}
</script>

<style scoped>
  .main_page_container {
    margin-top: 60px;  /*Why does it not affect TopBar?*/
  }
</style>

<!--Global style-->
<style>
  .round_border {
    border-radius: 4px;
  }
</style>
