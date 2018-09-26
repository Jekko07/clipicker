import Vue from 'vue'
import VueRouter from 'vue-router'
import Auth from '@okta/okta-vue'
import oktaConfig from '../config/okta.config'

import routes from './routes'
import store from '../store'

// add octa callback route
Vue.use(Auth, {
  issuer: oktaConfig.oidc.issuer,
  client_id: oktaConfig.oidc.clientId,
  redirect_uri: oktaConfig.oidc.redirectUri,
  scope: oktaConfig.oidc.scope
})

// todo: validate all under /clinic path
// todo: exclude /guest path


Vue.use(VueRouter)

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation
 */

export default function (/* { store, ssrContext } */) {
  const Router = new VueRouter({
    scrollBehavior: () => ({ y: 0 }),
    routes,
    store,


    // Leave these as is and change from quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    mode: process.env.VUE_ROUTER_MODE,
    base: process.env.VUE_ROUTER_BASE
  });

  return Router
}
