<template>
	<div>
		<h1 class="row">Users</h1>
		<form class="row">
			<div class="form-group">
				<input type="text" v-model="filterText" class="form-control" placeholder="Filter users">
			</div>
		</form>
		<div class="row">
			<table class="table table-hover">
				<thead>
					<tr>
						<th >User</th>
						<th >Activated</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="user in filteredUsers">
						<td>{{ user.email }}</td>
						<td><input type="checkbox" :checked="user.enabled" @click="updateUser(user)"></td>
						<td><b-button @click="openUserOrders(user)">Orders</b-button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
export default {
	name: 'UsersForm',
	data: function() {
		return {
			users: [],
			filterText: ''
		};
	},
	computed: {
		filteredUsers: function() {
			if (!this.filterText) return this.users;
			var t = this.filterText.toLowerCase();
			return this.users.filter(function(u){
				return u.email.toLowerCase().indexOf(t) != -1;
			});
		}
	},
	methods: {
		openUserOrders: function(user) {
			this.$router.push({
				name: 'OrdersEditPage',
				params: {
					user: user.email
				},
				query: {
					page: 0
				}
			});
		},
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
