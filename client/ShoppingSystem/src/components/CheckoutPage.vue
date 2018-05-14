<template>
  <div class="checkout_page_container">
    <div class="payment_form_container">
      <form @submit.prevent="confirmAndPay">
        <input type="text" placeholder="Card Number" v-model="cardNumber">
        <input type="text" placeholder="Card Holder Name" v-model="cardHolderName">
        <input type="text" placeholder="Expiration Year" v-model="expirationYear">
        <input type="text" placeholder="Expiration Month" v-model="expirationMonth">
        <input type="text" placeholder="CCV Security Code" v-model="ccv">
        <input type="submit" value="Confirm and pay">
      </form>
    </div>
  </div>
</template>


<script>
  import axios from 'axios'

  export default {
    components: {},
    name: 'CheckoutPage',
    props : [],
    data () {
      return {
        cardNumber: '',
        cardHolderName: '',
        expirationYear: '',
        expirationMonth: '',
        ccv: ''
      }
    },
    methods: {
      //Should have a selection of payment methods?
      confirmAndPay: function() {
        console.log("Paying");

        let cardInfo = {
          number: this.cardNumber,
          holder: this.cardHolderName,
          exp_year: this.expirationYear,
          exp_month: this.expirationMonth,
          ccv: this.ccv,
          amount: 10
        };

        axios.post('/api/order/', cardInfo)
          .then(function(resp){
            console.log(resp)

          });
      },

    }

  }
</script>

<style scoped>
  .checkout_page_container {

  }
</style>
