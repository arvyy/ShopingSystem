<template>
	<div class="search_bar_container">
		<input class="search_bar" type="text" :placeholder="placeholder" v-model="searchText">
		<button v-on:click="$emit('do-search', searchText)">Search</button>
	</div>
</template>

<script>
export default {
	name: 'SearchBar',
	data () {
		return {
			placeholder: 'Search',
			searchText: ''
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

</style>
