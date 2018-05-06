<template>
  <div class="login_container">
	  <div class="login_form">
		<form action="/login" @submit="checkForm" method="POST">
		  <input type="text" name="username" placeholder="User Name" v-model="username">
		  <input type="password" name="password" placeholder="Password" v-model="password">
		  <input type="submit" value="Login">
		</form>
	  </div>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'LogInPage',
	data : function() {
		return {
			username: '',
			password: ''
		};
	},
    methods: {
		checkForm: function(e) {
			e.preventDefault();
			var t = this;
			//gaidys sitas, kazkaip gudriau turetu susidet parametrai
			axios.post('/login?username=' + encodeURIComponent(t.username) + 
							 '&password=' + encodeURIComponent(t.password))
				.then(function(resp){
				if (resp.data.success === true) {
					t.onLoginSuccess();
				}
			});
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
