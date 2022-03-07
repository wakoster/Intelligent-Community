import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/faceSystem'
    },
    {
      path: '/faceSystem',
      name: 'homePage',
      component: () => import('@/components'),
      redirect: '/faceSystem/faceRecognition',
      children: [{
        path: 'faceRecognition',
        name: 'faceRecognition',
        component: () => import('@/components/faceRecognition')
      },
      {
        path: 'test',
        name: 'test',
        component: () => import('@/components/test')
      }]
    }
  ]
})
