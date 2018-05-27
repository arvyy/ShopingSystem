<template>
	<div class="admin-container">
		<b-navbar toggleable="md" variant="info">
			<b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
			<b-navbar-brand @click="openSearch">Ill Shop</b-navbar-brand>
			<b-collapse is-nav id="nav_collapse">
			<b-navbar-nav>
				<b-nav-item v-for="nav in adminlinks" @click="openLink(nav.link)" class="navigation-link">{{nav.name}}</b-nav-item>
			</b-navbar-nav>
			</b-collapse>
		</b-navbar>
		<div class="main">
			<router-view class="container" />	
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
			return this.adminlinksmenu;
			/*
			return [{
				name: 'Admin menu',
				link: 'AdminNavigationPage'
			}].concat(this.adminlinksmenu);
			*/
		}
	},
	methods: {
		openSearch: function() {
			this.$router.push({name: 'Search'});
		},
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
/*
.navigation {
	background-color: magenta;
	display: flex;
}
*/

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
