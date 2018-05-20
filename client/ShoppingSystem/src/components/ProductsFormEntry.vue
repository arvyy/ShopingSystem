<template>
			<form class="p-6" @submit="onSubmit" enctype="multipart/form-data">
				<h2>{{formHeader}}</h2>
				<p>
				<label for="product-form-productid">
					Id:<br><input type="text" :required="isNew" :disabled="!isNew" id="product-form-productid" name="productId" v-model="product.productId">
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
				<button v-if="!isNew" @click="onDelete">Delete</button>
			</form>
</template>

<script>
import axios from 'axios'
export default {

	name: 'ProductsFormEntry',
	
	props: ['isNew', 'productId'],
	
	data: function() {
		return {
			product: {},
			category: ''
		};
	},

	watch: {
		'$route': function() {
			this.loadProduct();
		}
	},

	computed: {
		formHeader: function() {
			return this.isNew? 'Creating new product' : 'Editing ' + this.product.name;
		},
		saveText: function() {
			return this.isNew? 'Create' : 'Update';
		}
	},

	mounted: function() {
		this.loadProduct();	
	},

	methods: {

		loadProduct: function() {
			var t = this;
			if (this.isNew) {
				this.product = {};
				this.category = {};
				return;
			}
			axios.get('/api/product/id/' + this.productId).then(function(resp){
				t.product = resp.data;
				if (t.product.category) {
					t.category = t.product.category.name;
				} else {
					t.category = '';
				}
			});
		},

		onSubmit: function(e) {
			e.preventDefault();
			var t = this;
			var config = { headers: { 'Content-Type': 'multipart/form-data' } };
			var fd = new FormData(e.target);
			fd.append('isNew', this.isNew);
			if (!this.isNew) {
				fd.append('productId', this.productId);
				//else ateina is formos
			}
			axios.post('/api/product', fd, config).then(function(resp){
				t.$router.push({name: 'ProductsForm'});
			});
		},
		onDelete: function() {
			axios.delete('/api/product/id/' + this.productId).then(function(resp){
				t.$router.push({name: 'ProductsForm'});
			});	
		}
	}
}
</script>
