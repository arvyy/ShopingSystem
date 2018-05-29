<template>

	<div class="order">
		<b-row>
			<h2>{{createdAt}}</h2>
		</b-row>
		<b-row>
			<b-table striped hover outlined :items="orderItems" :fields="fields"></b-table>
		</b-row>
		<b-row v-if="!editable">
			<h2>{{order.state}}</h2>
		</b-row>
		<b-row v-if="editable">
			<b-form-select @change="$emit('order-state-change', $event)" v-model="order.state" :options="states" class="mb-3" size="sm" />
		</b-row>
		<hr>
	</div>
</template>

<script>
export default {
	name: 'Order',
	props: ['order', 'editable', 'states'],
	computed: {
		orderItems: function() {
			return this.order.products;
		},
		createdAt: function() {
			return (this.order && this.order.payment)? this.order.payment : '';
		}
	},
	data: function() {
		return {
			fields: [{
				key: 'name',
				label: 'Product'
			}, {
				key: 'price',
				label: 'Price'
			}, {
				key: 'count',
				label: 'Count'
			}]	
		};
	}
}
</script>

<style scoped>
.order {
	margin-bottom: 40px;
}
</style>
