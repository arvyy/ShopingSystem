<template>
	<div class="d-flex flex-column">
		<div class="p-1">
			<button class="btn btn-default float-left" @click="createProduct">Create</button>
			<button class="btn btn-default float-left">Import</button>
			<button class="btn btn-default float-left">Export</button>
		</div>
		<div class="p-10 d-flex flex-row">
			<div class="p-6">
					<table class="table table-hover">
						<thead>
						<tr>
							<th>Id</th>
							<th>Name</th>
						</tr>
						</thead>
						<tbody>
						<tr v-for="product in products" 
		  @click="editProduct(product.productId)">
							<td>{{product.productId}}</td>
							<td>{{product.name}}</td>
						</tr>
						</tbody>
					</table>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
export default {
	name: 'ProductsForm',
	data: function() {
		return {
			products: []
		};
	},
	methods: {
		loadProducts: function() {
			var t = this;
			axios.get('/api/product/list').then(function(resp){
				t.products = resp.data;	
			});	
		},
		createProduct: function() {
			this.$router.push({name: 'ProductsFormEntryNew'});
		},
		editProduct: function(productId) {
			this.$router.push({
				name: 'ProductsFormEntry', 
				params: {
					productId: productId
				}
			});
		}
	},
	mounted: function() {
		this.loadProducts();
	}
}
</script>

<style scoped>
.products-form {
	flex: 1;
	display: flex;
	overflow: hidden;
}

.products-page {
	height: 100%;
	display: flex;
	flex-direction: column;
}

.form-toolbar button {
	float: left;
}

.left-panel {
	height: 100%;
	flex: 1;
	display: flex;
	flex-direction: column;
}

.right-panel {
	flex: 1;
}

.right-panel p {
	text-align: left;
}

.right-panel p input {
	width: 95%;
}

div.table-cnt {
	flex: 1;
	overflow: scroll;
}

table {
	width: 100%;
}

.id-col {
	width: auto;
}

.name-col {
	width: 100%;
}
</style>
