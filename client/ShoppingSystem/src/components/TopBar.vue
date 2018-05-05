<template>
	<div class="toolbar">
		<div class="toolbar-item" v-if="isLogedIn">{{ user }}</div>
		<button v-on:click="openLoginPage" class="toolbar-item" v-if="!isLogedIn">Log in</button>
	</div>
</template>

<script>
	export default {
		name: 'TopBar',
		props: ['user'],
		methods: {
			openLoginPage: function() {
			   this.$router.push({name: 'Login'});
			}
		},
		computed: {
			isLogedIn: function() {
				return this.user? true : false;
			}
		}
	}
</script>

<style scoped>
	.toolbar-item {
		height: 100%;
	}

	div.toolbar {
		position: fixed;
		display: flex;
		flex-direction: row-reverse;
		top: 0;
		left: 0;
		height: 40px;
		width: 100%;
		background-color: magenta;
	}
</style>
