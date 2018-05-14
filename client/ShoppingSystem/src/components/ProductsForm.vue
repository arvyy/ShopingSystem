<template>
	<div class="products-page">
		<div class="form-toolbar">
			<button @click="createProduct">Create</button>
			<button>Import</button>
			<button>Export</button>
		</div>
		<div class="products-form">
			<div class="left-panel">
				<div class="table-cnt">
					<table class="admin-data-table">
						<tr>
							<th class="id-col">Id</th>
							<th class="name-col">Name</th>
						</tr>
						<tr v-for="product in products" 
		  :class="{ selectedrow : product.productId == selectedId}"
		  @click="editProduct(product.productId)">
							<td>{{product.productId}}</td>
							<td>{{product.name}}</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="right-panel" v-if="!showForm && isLoading">Loading...</div>
			<form class="right-panel" v-if="showForm" @submit="onSubmit" enctype="multipart/form-data">
				<h2>{{formHeader}}</h2>
				<p>
				<label for="product-form-productid">
					Id:<br><input type="text" :required="selectedCreate" :disabled="!selectedCreate" id="product-form-productid" name="productId" v-model="product.productId">
				</label>
				</p>
				<p>
				<label for="product-form-name">
					Name:<br><input type="text" required id="product-form-name" name="name" v-model="product.name">
				</label>
				</p>
				<p>
				<label for="product-form-desc">
					Description:<br><textarea required id="product-form-desc" name="description" v-model="product.description"></textarea>
				</label>
				</p>
				<p>
				<label for="product-form-price">
					Price:<br><input type="number" required step="0.01" id="product-form-price" name="price" v-model="product.price">
				</label>
				</p>
				<p>
				<label for="product-form-category">
					Category:<br><input type="text" name="categoryName" id="product-form-category" v-model="category">
				</label>
				</p>
				<p>
				<label for="product-form-file">
					Image:<br><input type="file" name="file" id="product-form-file">
				</label>
				</p>
				<input type="submit" :value="saveText">
				<button v-if="selectedId" @click="onDelete">Delete</button>
			</form>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
export default {
	name: 'ProductsForm',
	data: function() {
		return {
			filterText: '',
			products: [],
			selectedId: '',
			selectedCreate: false,
			product: {},
			category: '',
			formHeader: '',
			isLoading: false
		};
	},
	computed: {
		showForm: function() {
			return (this.selectedId || this.selectedCreate) && !this.isLoading;
		},
		saveText: function() {
			return this.selectedCreate? 'Create' : 'Update';
		}
	},
	methods: {
		loadProducts: function(editIdAfterLoad) {
			var t = this;
			axios.get('/api/product/list').then(function(resp){
				t.products = resp.data;	
				if (editIdAfterLoad) {
					t.editProduct(editIdAfterLoad);
				}
			});	
		},
		editProduct: function(id) {
			var t = this;
			this.isLoading = true;
			axios.get('/api/product/id/' + id).then(function(resp){
				t.product = resp.data;
				t.selectedId = id;
				t.selectedCreate = false;
				t.formHeader = 'Editing product ' + t.product.name;
				if (t.product.category) {
					t.category = t.product.category.name;
				} else {
					t.category = '';
				}
				t.isLoading = false;
			});
			this.selectedId = id;
			this.selectedCreate = false;
		},
		createProduct: function() {
			this.selectedId = '';
			this.selectedCreate = true;
			this.product = {};
			this.category = '';
			this.formHeader = 'Creating new product';
		},
		onSubmit: function(e) {
			e.preventDefault();
			var t = this;
			var config = { headers: { 'Content-Type': 'multipart/form-data' } };
			var fd = new FormData(e.target);
			fd.append('isNew', this.selectedCreate);
			if (!this.selectedCreate) {
				fd.append('productId', this.selectedId);
				//else ateina is formos
			}
			axios.post('/api/product', fd, config).then(function(resp){
				t.loadProducts(t.selectedCreate? t.product.productId : t.selectedId);
			});
		},
		onDelete: function() {
			axios.delete('/api/product/id/' + this.selectedId).then(function(resp){
				t.loadProducts();
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
