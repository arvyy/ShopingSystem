<template>
	<div>
		<Order v-for="order in ordersPages.content" :order="order" :editable="false"></Order>
		<b-pagination align="center" @input="onPageChange($event - 1)" size="md" :total-rows="ordersPages.totalElements" :value="ordersPages.number + 1" :per-page="ordersPages.size"/>
	</div>
</template>

<script>
import axios from 'axios'
import Order from './Order.vue'
export default {
	name: 'OrdersPage',
	props: ['page'],
	components: {
		'Order': Order
	},
	data: function() {
		return {
			ordersPerPage: 10,
			ordersPages: {}
		};
	},
	watch: {
		'$route': function(to, from) {
			this.loadOrders();
		}
	},
	methods: {
		onPageChange: function(newPage) {
			this.$router.push({name: 'OrdersPage', query: {
				page: newPage
			}});
		},
		loadOrders: function() {
			var t = this;
			var url = '/api/order/my';
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
	}	
}
</script>

