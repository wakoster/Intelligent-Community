import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/components/login')
    },
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/homePage',
      name: 'homePage',
      component: () => import('@/components/homePage')
    },
    {
      path: '/settingPage',
      name: 'settingPage',
      component: () => import('@/components/settingPage'),
      redirect: '/settingPage/installationPackageManagement',
      children: [{
        path: 'installationPackageManagement',
        name: 'installationPackageManagement',
        component: () => import('@/components/settingPage/pages/installationPackageManagement.vue')
      }]
    }
  ]
})
