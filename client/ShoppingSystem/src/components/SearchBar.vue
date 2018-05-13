<template>
	<div class="search_bar_container">
		<div>
			<input class="search_bar" type="text" placeholder="Search" v-model="searchText">
			<button @click="onSearch">Search</button>
		</div>
		<div class="categories" v-if="categories.length">
			Categories:
			<div v-for="cat in categories" class="category" :class="{selected : cat.name == category}" @click="select(cat)">
				{{cat.name}}
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'SearchBar',
	props: ['categories'],
	data () {
		return {
			searchText: '',
			category: ''
		};
	},
	methods: {
		select: function(cat) {
			if (cat.name != this.category) {
				this.category = cat.name;	
			} else {
				this.category = "";
			}
		},
		onSearch: function() {
			this.$emit('do-search', {
				searchText: this.searchText,
				category: this.category
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
