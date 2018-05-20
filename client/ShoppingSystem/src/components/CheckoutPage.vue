<template>
  <div class="checkout_page_container">
    <div class="shopping_cart_container">
      <div class="container">
        <b-table striped hover outlined :items="productsProvider"></b-table>
      </div>
    </div>

    <!--<div class="payment_form_container">-->
      <!--<form @submit.prevent="confirmAndPay">-->
        <!--<input type="text" placeholder="Card Number" v-model="cardNumber">-->
        <!--<input type="text" placeholder="Card Holder Name" v-model="cardHolderName">-->
        <!--<input type="text" placeholder="Expiration Year" v-model="expirationYear">-->
        <!--<input type="text" placeholder="Expiration Month" v-model="expirationMonth">-->
        <!--<input type="text" placeholder="CCV Security Code" v-model="ccv">-->
        <!--<input type="submit" value="Confirm and pay">-->
      <!--</form>-->
    <!--</div>-->

    <div class="container w-50">
      <b-form  @submit.prevent="confirmAndPay">
        <b-card bg-variant="light">
          <b-form-group horizontal
                        breakpoint="lg"
                        label="Payment Information"
                        label-size="lg"
                        label-class="font-weight-bold pt-0"
                        class="mb-0">
            <b-form-group label="Card Number:"
                          label-class="font-weight-bold">
              <b-form-input type="text"
                            v-model="cardNumber"
                            required
                            placeholder="Your Card Number">
              </b-form-input>
            </b-form-group>
            <b-form-group label="Card Holder Name:"
                          label-class="font-weight-bold">
              <b-form-input type="text"
                            v-model="cardHolderName"
                            required
                            placeholder="Full Name">
              </b-form-input>
            </b-form-group>
            <b-form-row>
              <b-col>
                <b-form-group label="Expiration Month:"
                                   label-class="font-weight-bold">
                <b-form-input type="text"
                              v-model="expirationMonth"
                              required
                              placeholder="MM">
                </b-form-input>
              </b-form-group>
              </b-col>
              <b-col>
                <b-form-group label="Expiration Year:"
                              label-class="font-weight-bold">
                  <b-form-input type="text"
                                v-model="expirationYear"
                                required
                                placeholder="YY">
                  </b-form-input>
                </b-form-group>
              </b-col>
              <b-col>
                <b-form-group label="CVV Security Code:"
                              label-class="font-weight-bold">
                  <b-form-input type="text"
                                v-model="cvv"
                                required
                                placeholder="CVV">
                  </b-form-input>
                </b-form-group>
              </b-col>
            </b-form-row>
          </b-form-group>
          <b-button type="submit" variant="primary">Confirm And Pay</b-button>
        </b-card>
      </b-form>
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
        cvv: ''
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
          exp_year: "20" + this.expirationYear,
          exp_month: this.expirationMonth,
          cvv: this.cvv,
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

  .b-form-group {
    text-align: left;
  }

</style>
