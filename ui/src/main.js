// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import router from './router';
import App from './App.vue';
import store from './store/store';
import axios from 'axios';

/* Import Bootstrap */
import 'bootstrap';

/* Import vue-i18n */
import VueI18n from 'vue-i18n'

/* Loarding Overlays */
import Loading from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/vue-loading.css';

/* Notification */
import Notifications from 'vue-notification';


window.$ = window.jQuery = require('jquery');

Vue.use(VueI18n);
Vue.use(Notifications);

/*
import 'bootstrap/dist/css/bootstrap.min.css';
*/

Vue.prototype.$axios = axios;

/* Init Loarding Overlays */
Vue.use(Loading, {
    container: null,
    loader: 'dots',
    color: '#DF691A',
    backgroundColor: '#4E5D6C',
    width: 200,
    height: 200,
    zIndex: 2000,
});

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  store,
  el: '#app',
  template: '<App/>',
  components: { App }
})
