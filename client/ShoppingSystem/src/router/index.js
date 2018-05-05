import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '@/components/MainPage'
import ProductPage from '@/components/ProductPage'
import LoginPage from '@/components/LogInPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'MainPage',
      component: MainPage
    }, {
		path: '/product/:productId',
		name: 'ProductPage',
		component: ProductPage,
		props: true
	}, {
		path: '/login',
		name: 'Login',
		component: LoginPage
	}
  ]
})
