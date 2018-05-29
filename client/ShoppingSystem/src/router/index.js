import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '@/components/MainPage'
import SearchPage from '@/components/SearchPage'
import ProductPage from '@/components/ProductPage'
import LoginPage from '@/components/LogInPage'
import AdminPage from '@/components/AdminPage'
import AdminNavigationPage from '@/components/AdminNavigationPage'
import ProductsForm from '@/components/ProductsForm'
import ProductsFormEntry from '@/components/ProductsFormEntry'
import UsersForm from '@/components/UsersForm'
import CheckoutPage from '@/components/CheckoutPage'
import PreferencesPage from '@/components/PreferencesPage'
import OrdersPage from '@/components/OrdersPage'
import OrdersEditPage from '@/components/OrdersEditPage'

Vue.use(Router)

export default new Router({
	routes: [
		{
			path: '/',
			component: MainPage,
			children: [{
				path: '',
				name: 'Search',
				props : function(route) {
					return {
						searchtext: route.query.text,
						page: route.query.page,
						category: route.query.category
					};
				},
				component: SearchPage
			}, {
				path: 'orders',
				name: 'OrdersPage',
				props: function(route) {
					return {
						page: route.query.page,
						editable: false
					};
				},
				component: OrdersPage
			}, {
				path: 'product/:productId',
				name: 'ProductPage',
				component: ProductPage,
				props: true
			}, {
			  path: '/checkout',
        name: 'Checkout',
        component: CheckoutPage
      }, {
        path: '/preferences',
        name: 'Preferences',
        component: PreferencesPage
      }]
		}, {
			path: '/login',
			name: 'Login',
			component: LoginPage
		}, {
			path: '/admin/main',
			name: 'AdminNavigationPage',
			component: AdminNavigationPage
		}, {
			path: '/admin',
			component: AdminPage,
			children: [{
				path: 'orders/user/:user',
				name: 'OrdersEditPage',
				component: OrdersEditPage,
				props: function(route) {
					return {
						page: route.query.page,
						user: route.params.user
					};
				}
			}, {
				path: 'users',
				name: 'UsersForm',
				component: UsersForm
			}, {
				path: 'products',
				name: 'ProductsForm',
				component: ProductsForm
			}, {
				path: 'products/edit/:productId',
				name: 'ProductsFormEntry',
				component: ProductsFormEntry,
				props: function(route){
					return {
						isNew: false,
						productId: route.params.productId
					};
				}
			}, {
				path: 'products/create',
				name: 'ProductsFormEntryNew',
				component: ProductsFormEntry,
				props: function(route){
					return {
						isNew: true,
						productId: null
					};
				}
			}]
		}
	]
})
