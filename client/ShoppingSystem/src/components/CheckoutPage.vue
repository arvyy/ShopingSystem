<template>
  <div class="checkout_page_container">
    <div class="shopping_cart_container">
      <div class="container">
        <b-table striped hover outlined :items="productsProvider"></b-table>
      </div>
    </div>

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

    <!--<div class="container">-->
      <!--<b-form @submit.prevent="confirmAndPay">-->
        <!--<b-form-group id="exampleInputGroup1"-->
                      <!--label="Card Number:"-->
                      <!--label-for="exampleInput1"-->
                      <!--description="We'll never share your email with anyone else."-->
                      <!--label-text-align="left">-->
          <!--<b-form-input id="exampleInput1"-->
                        <!--type="email"-->
                        <!--v-model="cardNumber"-->
                        <!--required-->
                        <!--placeholder="Enter email">-->
          <!--</b-form-input>-->
        <!--</b-form-group>-->
        <!--<b-form-group id="exampleInputGroup2"-->
                      <!--label="Your Name:"-->
                      <!--label-for="exampleInput2">-->
          <!--<b-form-input id="exampleInput2"-->
                        <!--type="text"-->
                        <!--v-model="cardHolderName"-->
                        <!--required-->
                        <!--placeholder="Enter name">-->
          <!--</b-form-input>-->
        <!--</b-form-group>-->
        <!--<b-button type="submit" variant="primary">Submit</b-button>-->
      <!--</b-form>-->
    <!--</div>-->

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
      productsProvider: function() {
        let promise = axios.get('/api/shoppingcart');

        return promise.then((resp) => {
          var products = [];
          var combinedPrice;
          var totalPrice = 0;

          for(var productArray of resp.data) {
            combinedPrice = productArray[0].price * productArray[1];
            totalPrice += combinedPrice;

            products.push({
              name: productArray[0].name,
              quantity: productArray[1],
              price: combinedPrice
            });
          }

          products.push({
            name: '',
            quantity: '',
            price: totalPrice
          })
          return(products || [])
        })
      },

      confirmAndPay: function() {
        console.log("Paying");

        let cardInfo = {
          number: this.cardNumber,
          holder: this.cardHolderName,
          exp_year: this.expirationYear,
          exp_month: this.expirationMonth,
          ccv: this.ccv,
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

  .items_and_price_container {

  }
</style>
