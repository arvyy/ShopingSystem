<template>
	<div>
		<form class="row form-inline justify-content-center">
			<div class="form-group">
				<label for="searchField">Search</label>
				<div class="col-sm-5">
					<b-form-input v-model="localSearchText" type="text" id="searchField" />
				</div>
			</div>

			<div class="form-group">
				<label for="categoryField">in</label>
				<div class="col-sm-5">
					<b-form-select v-model="localCategory" :options="categoryOptions" />
				</div>
			</div>
			<!--div class="form-group">
				Search <input type="text" v-model="searchText" class="form-control" placeholder="Search">
				Category 
				<b-button @click="onSearch">Search</b-button>
			</div-->
			<b-button @click="onSearch">Search</b-button>
		</form>
		<!--div class="row">
			<input class="search_bar" type="text" placeholder="Search" v-model="searchText">
			<button @click="onSearch">Search</button>
		</div -->
		<!--div class="row justify-content-center" v-if="categories.length">
			Categories:
			<div v-for="cat in categories" class="category" :class="{selected : cat.name == category}" @click="select(cat)">
				{{cat.name}}
			</div>
		</div -->
	</div>
</template>

<script>
export default {
	name: 'SearchBar',
	props: ['categories', 'searchtext', 'category'],
	watch: {
		searchtext: function(st) {
			console.log(st);
			this.localSearchText = st;
		},
		category: function(c) {
			console.log(c);
			this.localCategory = c;
		}
	},		
	data () {
		return {
			localSearchText: '',
			localCategory: ''
		};
	},
	computed: {
		categoryOptions: function() {
			return [{
				value: "",
				text: 'All categories'
			}].concat(this.categories.map(function(c){
				return {
					value: c.name,
					text: c.name
				};
			}));
		}
	},
	methods: {
		onSearch: function() {
			this.$emit('do-search', {
				searchText: this.localSearchText,
				category: this.localCategory
			});
		}
	}

}
</script>

<style scoped>
.search_bar {
	width: 20vw;
	height: 2vw;
	font-size: 1.5vw;
}

.category {
	display: inline-block;
	padding: 5px 10px 5px 10px;
	margin: 5px;
	border: gray 1px solid;
}

.category.selected {
	background-color: lightblue;
}

</style>
