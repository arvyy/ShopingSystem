<template>
  <div class="alert_page_container">
    <div class="alert_container w-50 mx-auto">
      <b-alert :show="dismissCountDown"
               fade
               :variant="alertVariant"
               @dismissed="dismissCountDown=0">
        {{alertMessage}}
      </b-alert>
    </div>
  </div>
</template>


<script>
  export default {
    components: {},
    name: 'Alert',
    props : [],
    data () {
      return {
        alertMessage: '',
        dismissCountDown: 0,
        defaultDismissCount: 5,
        alertVariant: 'success'
      }
    },

    methods: {
      showAlert (alertMessage, dismissCount) {
        console.log("Showing alert")
        if (!dismissCount) {
          dismissCount = this.defaultDismissCount;
        }

        this.alertMessage = alertMessage;
        this.dismissCountDown = dismissCount;
      },
      showSuccessAlert (alertMessage, dismissCount) {
        this.alertVariant = 'success'
        this.showAlert(alertMessage, dismissCount)
      },
      showErrorAlert (alertMessage, dismissCount) {
        this.alertVariant = 'danger'
        if (typeof alertMessage === 'undefined' || !alertMessage || alertMessage == "") {
          alertMessage = "Error connecting to server"
        }
        else {
          alertMessage = "Error: " + alertMessage;
        }
        this.showAlert(alertMessage, dismissCount)
      }
    }

  }
</script>

<style scoped>
  /*.alert_page_container {*/
    /*bottom: 5%;*/
  /*}*/

</style>
