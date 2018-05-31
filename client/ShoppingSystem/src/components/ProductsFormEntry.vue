<template>
	<div>
		<h1 class="row">{{formHeader}}</h1>
			<form @submit="onSubmit" enctype="multipart/form-data">
				<div class="row form-group">
					<label for="product-form-productid">Id</label>
					<input type="text" class="form-control" :required="isNew" :disabled="!isNew" id="product-form-productid" name="productId" v-model="product.productId">
				</div>
				<div class="row form-group">
					<label for="product-form-name">Name</label>
					<input type="text" class="form-control" required id="product-form-name" name="name" v-model="product.name">
				</div>
				<div class="row form-group">
					<label for="product-form-desc">Description</label>
					<textarea class="form-control" required id="product-form-desc" name="description" v-model="product.description"></textarea>
				</div>
				<div class="row form-group">
					<label for="product-form-price">Price</label>
					<input class="form-control" type="number" required step="0.01" id="product-form-price" name="price" v-model="product.price">
				</label>
				</div>
				<div class="row form-group">
					<label for="product-form-category">Category</label>
					<input class="form-control" type="text" name="categoryName" id="product-form-category" v-model="category">
				</div>
				<div v-if="!product.imageUrl" class="row form-group">
					<label for="product-form-file">Image</label>
					<input class="form-control" type="file" name="file" id="product-form-file">
				</label>
				</div>
				<div class="row form-group" v-if="product.imageUrl">
					<div class="img-container col-4">
						<img :src="product.imageUrl">
					</div>
					<div class="col-8 form-group">
						<b-form-select v-model="imageAction" :options="imageActionOptions" class="row mb-3" size="sm" />
						<div v-if="imageAction == 1" class="row form-group">
							<label for="product-form-file">New image</label>
							<input class="form-control" type="file" name="file" id="product-form-file">
						</div>
					</div>
				</div>
				<div class="row form-group">
					<b-btn type="submit" >{{saveText}}</b-btn>
					<b-btn v-if="!isNew" @click="onDelete">Delete</b-btn>
				</div>
			</form>

			<b-modal ref="deleteModal" @ok="doDelete">
				The product will be permamently deleted
			</b-modal>
		</div>
</template>

<script>
import axios from 'axios'
export default {

	name: 'ProductsFormEntry',
	
	props: ['isNew', 'productId'],
	
	data: function() {
		return {
			product: {},
			category: '',
			imageAction: 0,
			imageActionOptions: [
				{value: 0, text: 'Keep image'},
				{value: 1, text: 'Change image'},
				{value: 2, text: 'Delete image'}
			]
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
			var url = this.isNew? '/api/product' : '/api/product/update';
			var t = this;
			var config = { headers: { 'Content-Type': 'multipart/form-data' } };
			var fd = new FormData(e.target);
			fd.append('isNew', this.isNew);
			fd.append('imageUrl', this.product.imageUrl);
			if (!this.isNew) {
				fd.append('productId', this.productId);
				//else ateina is formos
				fd.append('deleteImage', this.imageAction == 2);
			}
			axios.post(url, fd, config).then(function(resp){
				t.$router.push({name: 'ProductsForm'});
			});
		},
		onDelete: function() {
			this.$refs.deleteModal.show();
		},
		doDelete: function() {
			var t = this;
			axios.delete('/api/product/id/' + this.productId).then(function(resp){
				t.$router.push({name: 'ProductsForm'});
			});	
		}
	}
}
</script>

<style scoped>
.img-container {
  width: 200px;
}

.img-container img {
  max-height: 100%;
  max-width: 100%;
}
</style>
