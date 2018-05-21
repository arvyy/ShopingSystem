<template>
  <div class="alert_container w-50 mx-auto">
    <b-alert :show="dismissCountDown"
             fade
             :variant="alertVariant"
             @dismissed="dismissCountDown=0">
      {{alertMessage}}
    </b-alert>
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
        defaultDismissCount: 3,
        alertVariant: 'success'
      }
    },

    methods: {
      showAlert (alertMessage, dismissCount) {
        if (!dismissCount) {
          dismissCount = this.defaultDismissCount;
        }

        this.alertMessage = alertMessage;
        this.dismissCountDown = dismissCount;
      },
      showSuccessAlert (alertMessage, dismissCount) {
        this.alertVariant = 'sucess'
        this.showAlert(alertMessage, dismissCount)
      },
      showDangerAlert (alertMessage, dismissCount) {
        this.alertVariant = 'danger'
        this.showAlert(alertMessage, dismissCount)
      }
    }

  }
</script>

<style scoped>


</style>
