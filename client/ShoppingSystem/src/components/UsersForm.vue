<template>
	<div class="table-cnt">
		<table class="admin-data-table">
			<tr>
				<th class="user-col">User</th>
				<th class="activated-col">Activated</th>
			</tr>
			<tr v-for="user in users">
				<td>{{ user.email }}</td>
				<td><input type="checkbox" :checked="user.enabled" @click="updateUser(user)"></td>
			</tr>
		</table>
	</div>
</template>

<script>
import axios from 'axios'
export default {
	name: 'UsersForm',
	data: function() {
		return {
			users: []
		};
	},
	methods: {
		loadUsers: function() {
			var t = this;
			axios.get('/api/user/allusers').then(function(resp){
				t.users = resp.data;
			});
		},
		updateUser: function(user) {
			user.enabled = !user.enabled;
			var change = user.enabled? 'enable' : 'disable';
			axios.post('/api/user/' + change + '/' + encodeURIComponent(user.email)).then(function(resp){
				
			});
		}
	},
	mounted: function() {
		this.loadUsers();
	}
}
</script>

<style scoped>
div.table-cnt {
	overflow: auto;
}

table {
	width: 100%;
}

.user-col {
	width: 100%;
}

.activated-col {
	width: 30px;
}
</style>
