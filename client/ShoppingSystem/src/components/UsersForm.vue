<template>
	<div>
		<table>
			<tr>
				<th>User</th>
				<th>Activated</th>
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
<style>
