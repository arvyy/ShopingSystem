<template>
	<div class="adminpage panel panel-default">
		<div class="panel-heading">Admin Page</div>
		<div class="panel-body">
			<ul class="list-group">
			<li v-for="nav in adminlinks" class="list-group-item">
				<div><a @click="openAdminLink(nav.link)">{{nav.name}}</a></div>
			</li>
			</ul>
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
</style>
