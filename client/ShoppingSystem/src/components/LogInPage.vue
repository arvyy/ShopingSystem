<template>
  <div class="login_container">
	  <div class="login_form">
		<form action="/login" method="POST">
		  <input type="text" name="username" placeholder="User Name">
		  <input type="password" name="password" placeholder="Password">
		  <input type="submit" value="Login">
		</form>
	  </div>
  </div>
</template>

<script>
  export default {
    name: 'LogInPage',
    data () {
      return {
      }
    },
    methods: {

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
