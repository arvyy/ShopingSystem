<template>
	<div class="admin-container">
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

.admin-container {
	height: 100%;
	display: flex;
	flex-direction: column;
}

.navigation {
	background-color: magenta;
	display: flex;
	height: 20px;
}

.main {
	flex: 1;
	overflow: scroll;
}
</style>
