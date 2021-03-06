import "core-js/stable";
import "regenerator-runtime/runtime";
import 'promise-polyfill/src/polyfill';
import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import numeral from 'numeral';
const isDevelopment = process.env.NODE_ENV !== 'production';

Vue.filter('fmt', (value) => isNaN(value) ? showNaN() : (numeral(value).format('0,0'))); // displaying other groupings/separators is possible, look at the docs
// Vue.filter('ymd', (value) => moment(value).format('YYYYMM-DD')); // displaying other groupings/separators is possible, look at the docs
Vue.filter('fix', (value) => isNaN(value) ? showNaN() : (value != null ? value.toFixed(1) : 0)); // displaying other groupings/separators is possible, look at the docs
Vue.filter('fix3', (value) => isNaN(value) ? showNaN() : (value != null ? value.toFixed(3) : 0)); 

function showNaN(){
  return isDevelopment ? 'NaN' : 0;
}

Vue.config.productionTip = false

new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')
