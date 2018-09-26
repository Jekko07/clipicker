import Vue from 'vue'
import Vuex from 'vuex'
import createLogger from 'vuex/dist/logger';

import user from './user'
import announcement from './announcement'
import services from './services'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'
/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation
 */

export default new Vuex.Store({
  modules: {
    user,
    announcement,
    services,
  },
  strict: debug,
  plugins: debug ? [createLogger()] : []
})
