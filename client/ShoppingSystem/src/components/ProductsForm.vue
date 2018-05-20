<template>
	<div class="d-flex flex-column">
		<b-modal id="import-modal" @ok="doImport">
			<b-form-file v-model="importFile" :state="Boolean(importFile)" placeholder="Choose Excel file..."></b-form-file>
		</b-modal>
		<div class="p-1">
			<b-btn @click="createProduct">Create</b-btn>
			<b-btn v-b-modal="'import-modal'">Import</b-btn>
			<b-btn @click="exportProduct">Export</b-btn>
		</div>
		<div class="p-10">
			<div>
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
			products: [],
			importFile: null
		};
	},
	methods: {
		doImport: function() {
			var fd = new FormData();
			fd.append('file', this.importFile);
			var config = { headers: { 'Content-Type': 'multipart/form-data' } };
			axios.post('/api/excel/upload', fd, config).then(function(resp){
				t.$router.push({name: 'ProductsForm'});
			});
		},
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
		},
		exportProduct: function() {
			window.open('/api/excel/export');
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
