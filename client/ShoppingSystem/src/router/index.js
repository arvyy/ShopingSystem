import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '@/components/MainPage'
import SearchPage from '@/components/SearchPage'
import ProductPage from '@/components/ProductPage'
import LoginPage from '@/components/LogInPage'
import AdminPage from '@/components/AdminPage'
import ProductsForm from '@/components/ProductsForm'
import UsersForm from '@/components/UsersForm'
import CheckoutPage from '@/components/CheckoutPage'

Vue.use(Router)

export default new Router({
	routes: [
		{
			path: '/',
			component: MainPage,
			children: [{
				path: '/',
				name: 'Search',
				props : function(route) {
					return {
						searchText: route.query.text,
						page: route.query.page,
						category: route.query.category
					};
				},
				component: SearchPage
			}, {
				path: 'product/:productId',
				name: 'ProductPage',
				component: ProductPage,
				props: true
			}, {
			  path: '/checkout',
        name: 'Checkout',
        component: CheckoutPage
      }]
		}, {
			path: '/login',
			name: 'Login',
			component: LoginPage
		}, {
			path: '/admin',
			name: 'AdminPage',
			component: AdminPage,
			children: [{
				path: 'users',
				name: 'UsersForm',
				component: UsersForm
			}, {
				path: 'products',
				name: 'ProductsForm',
				component: ProductsForm
			}]
		}
	]
})
