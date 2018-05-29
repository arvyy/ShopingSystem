<template>
	<div>
		<Order v-for="order in ordersPages.content" :order="order" :editable="true" :states="states" @order-state-change="onOrderStateChange(order, $event)"></Order>
		<b-pagination align="center" @input="onPageChange($event - 1)" size="md" :total-rows="ordersPages.totalElements" :value="ordersPages.number + 1" :per-page="ordersPages.size"/>
	</div>
</template>

<script>
import axios from 'axios'
import Order from './Order.vue'
export default {
	name: 'OrdersAdminPage',
	props: ['page', 'user'],
	components: {
		'Order': Order
	},
	data: function() {
		return {
			ordersPerPage: 10,
			ordersPages: {},
			states: []
		};
	},
	watch: {
		'$route': function(to, from) {
			this.loadOrders();
		}
	},
	methods: {
		onOrderStateChange: function(order, newstate) {
			axios.put('/api/order/' + order.id + '?state=' + encodeURIComponent(newstate));
		},
		onPageChange: function(newPage) {
			var user = this.user;
			this.$router.push({name: 'OrdersEditPage', query: {
				page: newPage
			}, params: {
				user: user
			}});
		},
		loadStates: function() {
			var t = this;
			axios.get('/api/order/states').then(function(response){
				t.states = response.data;
			});
		},
		loadOrders: function() {
			var t = this;
			var url = '/api/order/user/' + encodeURIComponent(this.user);
			axios.get(url, {
				params: {
					size: t.ordersPerpage,
					page: t.page
				}
			}).then(function(response){
				t.ordersPages = response.data;
			});
		}
	},
	mounted: function() {
		this.loadOrders();
		this.loadStates();
	}	
}
</script>

