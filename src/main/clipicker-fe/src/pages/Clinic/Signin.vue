<template>
  <q-page id="clinic-homepage-container">
    <div id="sign-in-widget" v-show="showSignin"/>
  </q-page>
</template>

<script>
  import OktaSignIn from '@okta/okta-signin-widget';
  import oktaConfig from '../../config/okta.config'
  import appConfig from '../../config/app.config'

  export default {
    name: 'Signin',
    data() {
      return {
        logo: appConfig.logo,
        authenticated: false,
        authenticatedUser: {},
      }
    },
    computed: {
      async showSignin() {
        return await this.$clipicker.isAuthenticated();
      }
    },
    created() {
      this.$clipicker.setClinicType(true);
    },
    mounted: function () {
      const vm = this;
      this.widget = new OktaSignIn({
        /**
         * Note: when using the Sign-In Widget for an OIDC flow, it still
         * needs to be configured with the base URL for your Okta Org. Here
         * we derive it from the given issuer for convenience.
         */
        baseUrl: oktaConfig.oidc.issuer.split('/oauth2')[0],
        clientId: oktaConfig.oidc.clientId,
        redirectUri: oktaConfig.oidc.redirectUri,
        logo: this.logo,
        i18n: {
          en: {
            'primaryauth.title': 'Welcome to CliPicker',
            'registration.signup.label': 'Need an account?'
          }
        },
        authParams: {
          responseType: ['id_token', 'token'],
          issuer: oktaConfig.oidc.issuer,
          display: 'page',
          scopes: oktaConfig.oidc.scope.split(' ')
        },
        idps: [
          { type: 'GOOGLE', id: oktaConfig.oidc.idps.google },
          { type: 'FACEBOOK', id: oktaConfig.oidc.idps.facebook }
        ],
        features: {
          rememberMe: true,
          router: true,
          registration: true
        },
        registration: {
          click() {
            vm.$router.push({
              path: '/clinic/register'
            })
          }
        }
      })
      this.widget.renderEl(
        { el: '#sign-in-widget' },
        () => {
          /**
           * In this flow, the success handler will not be called because we redirect
           * to the Okta org for the authentication workflow.
           */
        },
        (err) => {
          throw err
        }
      )
    },
    destroyed () {
      // Remove the widget from the DOM on path change
      this.widget.remove()
    }
  }
</script>
<style lang="stylus">
  @import '~src/css/app.styl';

  #okta-sign-in a.link.js-help-link
    display none !important

  .auth-org-logo
    display: block;
    margin: 0 auto;
    max-width: 100px;
    max-height: 100px !important;

  #okta-sign-in.auth-container .button-primary
    okta-bg-color-fn()
    &:focus, &:hover, &:active
      okta-bg-color-fn()
</style>
