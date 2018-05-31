<template>
  <div class="checkout_page_container">
    <div class="shopping_cart_container">
      <div class="container">
        <b-table striped hover outlined :items="productsProvider"></b-table>
      </div>
    </div>

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
                            placeholder="Your Card Number"
                            minlength="16"
                            :maxlength="16">
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
                <b-form-input type="number"
                              v-model="expirationMonth"
                              required
                              placeholder="MM"
                              :minlength="2"
                              :maxlength="2"
                              min="0"
                              max="12">
                </b-form-input>
              </b-form-group>
              </b-col>
              <b-col>
                <b-form-group label="Expiration Year:"
                              label-class="font-weight-bold">
                  <b-form-input type="number"
                                v-model="expirationYear"
                                required
                                placeholder="YY"
                                :min="18"
                                :max="99">
                  </b-form-input>
                </b-form-group>
              </b-col>
              <b-col>
                <b-form-group label="CVV Security Code:"
                              label-class="font-weight-bold">
                  <b-form-input type="text"
                                v-model="cvv"
                                required
                                placeholder="CVV"
                                :minlength="3"
                                :maxlength="3">
                  </b-form-input>
                </b-form-group>
              </b-col>
            </b-form-row>
          </b-form-group>
		  <b-form-row>
			  <b-form-group label="Delivery address" label-class="font-weight-bold">
				  <b-form-input type="text" v-model="address" required></b-form-input>
			  </b-form-group>
		  </b-form-row>
          <b-button type="submit" variant="primary">Confirm And Pay</b-button>
        </b-card>
      </b-form>
    </div>

  </div>
</template>


<script>
  import axios from 'axios'
  import { NotificationBus } from './../NotificationBus.js';

  export default {
    components: {},
    name: 'CheckoutPage',
    props : [

    ],
    data () {
      return {
        cardNumber: '',
        cardHolderName: '',
        expirationYear: '',
        expirationMonth: '',
        cvv: '',
		address: ''
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
            price: totalPrice + ' €'
          })
          return(products || [])
        })
      },

      confirmAndPay: function() {
        let cardInfo = {
          number: this.cardNumber,
          holder: this.cardHolderName,
          exp_year: "20" + this.expirationYear,
          exp_month: this.expirationMonth,
          cvv: this.cvv,
        };

        var self = this;

        axios.post('/api/order/?address=' + encodeURIComponent(this.address), cardInfo)
          .then(function(resp){
            NotificationBus.$emit('success', 'Payment successful');
            self.onSucessfulUpdate()
          })
          .catch(function (error) {
            NotificationBus.$emit('error', error.response.data.Message)
          })
      },

      onSucessfulUpdate: function () {
		this.$emit('order-complete');
        this.$router.push({name: 'OrdersPage'});
      }
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
