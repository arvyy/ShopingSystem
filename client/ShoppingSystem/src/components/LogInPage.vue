<template>
	<div class="container">
		<h3>Login</h3>
		<form @submit="doLogin">
		<div class="form-group row">
		  <label for="inputEmailLogin" class="col-sm-2 col-form-label">Email</label>
		  <div class="col-sm-10">
			<input type="email" class="form-control" id="inputEmailLogin" placeholder="Email" v-model="username">
		  </div>
		</div>
		<div class="form-group row">
		  <label for="inputPasswordLogin" class="col-sm-2 col-form-label">Password</label>
		  <div class="col-sm-10">
			<input type="password" class="form-control" id="inputPasswordLogin" placeholder="Password" v-model="password">
		  </div>
		</div>
		<button type="submit" class="btn btn-primary">Login</button>
		</form>
		<hr>
		<h3>Sign up</h3>
		<form @submit="doSignup">
			<div class="form-group row">
			  <label for="inputEmailSignup" class="col-sm-2 col-form-label">Email</label>
			  <div class="col-sm-10">
				<input type="email" class="form-control" id="inputEmailSignup" placeholder="Email" v-model="signupUsername">
			  </div>
			</div>
			<div class="form-group row">
			  <label for="inputPasswordSignup" class="col-sm-2 col-form-label">Password</label>
			  <div class="col-sm-10">
				<input type="password" class="form-control" id="inputPasswordSignup" placeholder="Password" v-model="signupPassword">
			  </div>
			</div>
			<!--div class="form-group row">
			  <label for="inputPasswordSignupRepeat" class="col-sm-2 col-form-label">Repeat password</label>
			  <div class="col-sm-10">
				<input type="password" class="form-control" id="inputPasswordSignupRepeat" placeholder="Password">
			  </div>
			</div-->
			<button type="submit" class="btn btn-primary">Signup</button>
		</form>
	</div>
	<!--
	<div class="login_container">
		<div>
			<h3>Login</h3>
			<form action="/login" @submit="checkForm" method="POST">
				<input type="text" class="form-control" name="username" placeholder="User Name" v-model="username">
				<input type="password" class="form-control" name="password" placeholder="Password" v-model="password">
				<input type="submit" value="Login">
			</form>
			<hr>
			<h3>Sign up</h3>

		</div>
	</div>
	-->
</template>

<script>
import axios from 'axios'
import { NotificationBus } from './../NotificationBus.js';

export default {
	name: 'LogInPage',
	data : function() {
		return {
			username: '',
			password: '',

			signupUsername: '',
			signupPassword: ''
		};
	},
	methods: {
		doLogin: function(e) {
			e.preventDefault();
			var t = this;
			//gaidys sitas, kazkaip gudriau turetu susidet parametrai
			axios.post('/login?username=' + encodeURIComponent(t.username) +
				'&password=' + encodeURIComponent(t.password))
				.then(function(resp){
					if (resp.data.success === true) {
						t.onLoginSuccess();
					}
				})
        .catch(function (error) {
          NotificationBus.$emit('error', "Error: " + error.response.data.Message)
        })
		},
		doSignup: function(e) {
			e.preventDefault();
			var t = this;
			axios.post('/api/user/sign-up', {
				email: t.signupUsername,
				password: t.signupPassword
			}).then(function(resp){
				if (resp.data.success === true) {
					t.onLoginSuccess();
				}
			})
      .catch(function (error) {
        NotificationBus.$emit('error', "Error: " + error.response.data.Message)
      })
		},
		onLoginSuccess: function() {
			this.$router.go(-1);
		}
	},
	mounted : function() {

	},
	components: {

	}
}
</script>

<style scoped>
.login_container {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

</style>
