import Vue from 'vue'
import App from './App.vue'
// import Axios from 'axios'
// import VueAxios from 'vue-axios'

Vue.config.productionTip = false

//Vue.use(VueAxios, Axios);
import axios from 'axios'
Vue.prototype.$axios = axios

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI);


new Vue({
  render: h => h(App),
}).$mount('#app')
