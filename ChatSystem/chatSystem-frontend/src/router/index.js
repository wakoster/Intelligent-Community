import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/chatSystem'
    },
    {
      path: '/chatSystem',
      name: 'homePage',
      component: () => import('@/components'),
      redirect: '/chatSystem/chat',
      children: [{
        path: 'chat',
        name: 'chat',
        component: () => import('@/components/chat')
      },
      {
        path: 'contacts',
        name: 'contacts',
        component: () => import('@/components/contacts')
      },
      {
        path: 'userInfo',
        name: 'userInfo',
        component: () => import('@/components/userInfo')
      }]
    }
  ]
})
