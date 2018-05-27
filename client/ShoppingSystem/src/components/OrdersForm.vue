<template>
	<div>
		<h1 class="row">Orders</h1>
		<form class="row">
			<div class="form-group">
				<input type="text" v-model="usersFilter" class="form-control" placeholder="Filter users">
			</div>
		</form>
		<b-table striped hover :items="orders" :fields="fields" class="row"></b-table>
	</div>
</template>

<script>
import axios from 'axios'
export default {
	name: 'OrdersForm',
	data: function() {
		return {
			orders: [],
			usersFilter: '',
			fields: [{
				key: 'id',
				label: 'Order ID',
				sortable: true
			}, {
				key: 'user',
				label: 'User'
			}]
		};
	},
	methods: {
		loadOrders: function() {
			var t = this;
			axios.get('/api/order').then(function(resp){
				t.orders = resp.data;	
			});	
		},
	},
	mounted: function() {
		this.loadOrders();
	}
}
</script>
