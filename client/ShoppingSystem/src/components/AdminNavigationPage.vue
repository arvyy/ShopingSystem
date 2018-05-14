<template>
	<div class="adminpage">
		<div class="header"><h1>Admin Page</h1></div>
		<div>
			<div v-for="nav in adminlinks" class="box">
				<div><a @click="openAdminLink(nav.link)">{{nav.name}}</a></div>
			</div>
		</div>
	</div>
</template>

<script>
import AdminMenuJson from '../assets/adminmenu.json'
export default {
	name: 'AdminNavigationPage',
	data: function() {
		return {
			adminlinks: AdminMenuJson
		};
	},
	methods: {
		openAdminLink: function(link) {
			this.$router.push({name: link});
		}
	}
}
</script>

<style scoped>

	div.box {
		display: inline-block;
		padding: 10px;
		margin: 5px;
		border: gray solid 1px;
	}

</style>
