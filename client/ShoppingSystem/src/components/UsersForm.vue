<template>
	<div class="row">
		<table class="table table-hover">
			<thead>
			<tr>
				<th >User</th>
				<th >Activated</th>
			</tr>
			</thead>
			<tbody>
			<tr v-for="user in users">
				<td>{{ user.email }}</td>
				<td><input type="checkbox" :checked="user.enabled" @click="updateUser(user)"></td>
			</tr>
			</tbody>
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
