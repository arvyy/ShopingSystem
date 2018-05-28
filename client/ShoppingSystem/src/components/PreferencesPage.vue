<template>
  <div class="tab_container container">
    <b-card no-body>
      <b-tabs card>
        <b-tab title="Change Email" active>
           <b-form @submit.prevent="changeEmail">
            <b-form-group label="New Email:"
                          label-class="font-weight-bold">
              <b-form-input type="email"
                            v-model="newEmail"
                            required
                            placeholder="Email">
              </b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary">Submit</b-button>
          </b-form>
        </b-tab>
        <b-tab title="Change Password">
          <b-form @submit.prevent="changePassword">
            <b-form-group label="Old Password:"
                          label-class="font-weight-bold">
              <b-form-input type="password"
                            v-model="oldPassword"
                            required
                            placeholder="Old Password">
              </b-form-input>
            </b-form-group>
            <b-form-group label="New Password:"
                          label-class="font-weight-bold">
              <b-form-input type="password"
                            v-model="newPassword"
                            required
                            placeholder="New Password">
              </b-form-input>
            </b-form-group>
            <b-form-group label="Confirm New Password:"
                          label-class="font-weight-bold">
              <b-form-input type="password"
                            v-model="newPasswordConfirm"
                            required
                            placeholder="Confirm Password">
              </b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary">Submit</b-button>
          </b-form>
        </b-tab>
      </b-tabs>
    </b-card>

  </div>

</template>


<script>
  import axios from 'axios'
  import { NotificationBus } from './../NotificationBus.js';

  export default {
    name: 'PreferencesPage',
    props : [],
    data () {
      return {
        newEmail: '',
        newPassword: '',
        newPasswordConfirm: '',
        oldPassword: ''
      }
    },

    methods: {
      changeEmail: function () {
        let emailInfo = {
          email: this.newEmail
        };

        var self = this;

        axios.post('/api/user/update/email', emailInfo)
          .then(function(resp){
            NotificationBus.$emit('success', 'Email Updated');
            self.onSucessfulUpdate()
          })
          .catch(function (error) {
            NotificationBus.$emit('error', "Error: " + error.response.data.Message)
          })
      },
      changePassword: function () {
        let passwordInfo = {
          oldPassword: this.oldPassword,
          password: this.newPassword,
          confirmPassword: this.newPasswordConfirm,
        };

        var self = this;

        axios.post('/api/user/update/password', passwordInfo)
          .then(function(resp){
            NotificationBus.$emit('success', 'Password Updated');
            self.onSucessfulUpdate()
          })
          .catch(function (error) {
            NotificationBus.$emit('error', "Error: " + error.response.data.Message)
          })
      },
      onSucessfulUpdate: function() {
        this.newEmail = ''
        this.newPassword =''
        this.newPasswordConfirm = ''
        this.oldPassword = ''
      }
    }
  }
</script>

<style scoped>
  .b-form-group {
    text-align: left;
  }

</style>
