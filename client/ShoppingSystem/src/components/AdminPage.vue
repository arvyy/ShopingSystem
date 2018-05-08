<template>
	<div>
		<div class="navigation">
			<a class="navigation-link" @click="openUsersForm">Users</a>
			<a class="navigation-link" @click="openProductsForm">Products</a>
		</div>
		<div class="main">
			<router-view />	
		</div>
	</div>
</template>

<script>
export default {
	name: 'AdminPage',
	data : function() {
		return {
		};
	},
	methods: {
		openUsersForm: function() {
			this.$router.push({name: 'UsersForm'});
		},
		openProductsForm: function() {
			this.$router.push({name: 'ProductsForm'});
		}
	}
}
</script>
<style scoped>
.navigation {
	position: absolute;
	background-color: magenta;
	top: 0;
	left: 0;
	width: 100%;
	display: flex;
	height: 20px;
}
.main {
	margin-top: 20px;
}
</style>
