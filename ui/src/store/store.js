import Vue from 'vue';
import Vuex from 'vuex';
import dl4j from './dl4j/index.js';


Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    dl4j,
  },
});
