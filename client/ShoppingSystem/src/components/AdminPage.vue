<template>
	<div class="admin-container">
		<div class="navigation">
			<a v-for="nav in adminlinks" class="navigation-link" @click="openLink(nav.link)">{{nav.name}}</a>
		</div>
		<div class="main">
			<router-view />	
		</div>
	</div>
</template>

<script>
import AdminMenuJson from '../assets/adminmenu.json'
export default {
	name: 'AdminPage',
	data : function() {
		return {
			adminlinksmenu: AdminMenuJson
		};
	},
	computed: {
		adminlinks: function() {
			return [{
				name: 'Admin menu',
				link: 'AdminNavigationPage'
			}].concat(this.adminlinksmenu);
		}
	},
	methods: {
		openLink: function(link) {
			this.$router.push({name: link});
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
}

.navigation-link {
	margin: 5px 5px 5px 0px;
}

.navigation-link:hover {
	font-weight: bold;
}

.main {
	flex: 1;
	overflow: scroll;
}

/*
.main >>> table.admin-data-table {
	border-collapse: collapse;
	text-align: left;
}
.main >>> table.admin-data-table th {
	background-color: lightgray;
	border-bottom: gray 1px solid;
}
.main >>> table.admin-data-table td {
	padding: 3px;
}
.main >>> table.admin-data-table tr.selectedrow {
		background-color: #fd45a6;
}

.main >>> table.admin-data-table tr:hover {
		background-color: yellow;
}
*/

</style>
