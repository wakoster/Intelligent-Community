// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// 导入md5
import md5 from 'js-md5'
// 导入axios
import axios from 'axios'
// 导入cookie
import VueCookies from 'vue-cookies'

// 将这个axios实例加载到Vue的原型上，以便随时可以访问
const instance = axios.create({
  baseURL: 'http://localhost:1004'
})
Vue.prototype.$axios = instance

// 加载md5
Vue.prototype.$md5 = md5

Vue.config.productionTip = false

Vue.use(VueCookies)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
