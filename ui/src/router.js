import Vue from 'vue';
import Router from 'vue-router';

import Dashboard from 'pages/Dashboard';
import Models from 'pages/Models';
import ModelEditor from 'pages/model/ModelEditor';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {path: '/index.html', redirect: '/'},
    {
      path: '/',
      name: 'Dashboard',
      component: Dashboard,
    },
    {
      path: '/models',
      name: 'Models',
      component: Models,
    },
    {
      path: '/models/:id',
      name: 'ModelEditor',
      component: ModelEditor,
    },
  ]
});
