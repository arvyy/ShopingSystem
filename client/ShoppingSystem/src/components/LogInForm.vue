<template>
  <div class="login_form">
    <form action="/login" method="post">
      <input type="text" name="username" placeholder="User Name">
      <input type="password" name="password" placeholder="Password">
      <input type="submit" value="Submit">
    </form>
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


</style>
