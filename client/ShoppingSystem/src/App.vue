<template>
	<div id="app">
    <successErrorAlert ref="successErrorAlert" class="fixed-bottom w-75 mx-auto"></successErrorAlert>
		<router-view/>
	</div>
</template>

<script>
  import Alert from './components/Alert'
  import { NotificationBus } from './NotificationBus.js';

  export default {
    components: {
      'successErrorAlert': Alert
    },
    name: 'App',
    mounted() {
      var self = this;

      NotificationBus.$on('error', message => {
        self.$refs.successErrorAlert.showErrorAlert(message)
      });

      NotificationBus.$on('sucess', message => {
        self.$refs.successErrorAlert.showSuccessAlert(message)
      });
    }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    height: 100%;
  }


</style>
